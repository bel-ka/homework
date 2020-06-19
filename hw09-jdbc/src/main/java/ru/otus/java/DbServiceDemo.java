package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.core.model.Account;
import ru.otus.java.core.model.User;
import ru.otus.java.h2.DataSourceH2;
import ru.otus.java.jdbc.executor.DbExecutorImpl;
import ru.otus.java.jdbc.mapper.*;
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
        demo.createAccountTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbUserExecutor = new DbExecutorImpl<>();

        logger.info("------------------------");
        JdbcMapper<User> jdbcUserMapper = new JdbcMapperImpl<>(sessionManager, dbUserExecutor, User.class);
        User userToDb = new User(1, "Ivanov", 25);
        jdbcUserMapper.insert(userToDb);

        Optional<User> userFromDb = jdbcUserMapper.findById(userToDb.getId());
        userFromDb.ifPresentOrElse(
                crUser -> logger.info("created user:{}", crUser.toString()),
                () -> logger.info("user was not created")
        );

        DbExecutorImpl<Account> dbAccountExecutor = new DbExecutorImpl<>();
        JdbcMapper<Account> jdbcAccountMapper = new JdbcMapperImpl<>(sessionManager, dbAccountExecutor, Account.class);
        Account accountToDb = new Account(1, "testAccount", 1234);
        jdbcAccountMapper.insert(accountToDb);

        Optional<Account> accountFromDb = jdbcAccountMapper.findById(accountToDb.getNo());
        accountFromDb.ifPresentOrElse(
                crAccount -> logger.info("created Account:{}", crAccount.toString()),
                () -> logger.info("Account was not created")
        );

        accountToDb.setRest(678);
        jdbcAccountMapper.update(accountToDb);

        Optional<Account> updateAccountFromDb = jdbcAccountMapper.findById(accountToDb.getNo());
        updateAccountFromDb.ifPresentOrElse(
                crAccount -> logger.info("updated Account:{}", crAccount.toString()),
                () -> logger.info("Account was not update")
        );

        accountToDb.setType("testInOrUp");
        jdbcAccountMapper.insertOrUpdate(accountToDb);

        Account accountInToDb = new Account(2, "VISA", 9498);
        jdbcAccountMapper.insertOrUpdate(accountInToDb);
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        logger.info("table User created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table account(no long auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        logger.info("table Account created");
    }
}
