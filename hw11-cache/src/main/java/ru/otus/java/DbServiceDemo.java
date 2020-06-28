package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.core.model.User;
import ru.otus.java.h2.DataSourceH2;
import ru.otus.java.jdbc.executor.DbExecutorImpl;
import ru.otus.java.jdbc.mapper.JdbcMapper;
import ru.otus.java.jdbc.mapper.JdbcMapperImpl;
import ru.otus.java.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createUserTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbUserExecutor = new DbExecutorImpl<>();

        logger.info("------------------------");
        JdbcMapper<User> jdbcUserMapper = new JdbcMapperImpl<>(sessionManager, dbUserExecutor, User.class);
        User userToDb = new User(1, "Ivanov", 25);
        jdbcUserMapper.insert(userToDb);
        logger.info("------------------------");
        long beginSelectFromDB = System.currentTimeMillis();
        Optional<User> userFromDb = jdbcUserMapper.findById(userToDb.getId());
        long timeForSelectInDB = (System.currentTimeMillis() - beginSelectFromDB);
        userFromDb.ifPresentOrElse(
                crUser -> logger.info("created user:{}", crUser.toString()),
                () -> logger.info("user was not created")
        );
        logger.info(String.format("Time to select from DB = %s", timeForSelectInDB));

        long beginSelectFromCache = System.currentTimeMillis();
        Optional<User> userFromCache = jdbcUserMapper.findByIdInCache(userToDb.getId());
        long timeForSelectInCache = (System.currentTimeMillis() - beginSelectFromCache);
        userFromCache.ifPresentOrElse(
                crUser -> logger.info("created user:{}", crUser.toString()),
                () -> logger.info("user was not created")
        );
        logger.info(String.format("Time to select from cache = %s", timeForSelectInCache));
        assert (timeForSelectInCache < timeForSelectInDB);
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        logger.info("table User created");
    }
}
