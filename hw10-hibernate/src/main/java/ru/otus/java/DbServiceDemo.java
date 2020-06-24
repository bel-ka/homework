package ru.otus.java;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.core.dao.UserDao;
import ru.otus.java.core.model.Address;
import ru.otus.java.core.model.Phone;
import ru.otus.java.core.model.User;
import ru.otus.java.core.service.DBServiceUser;
import ru.otus.java.core.service.DbServiceUserImpl;
import ru.otus.java.hibernate.HibernateUtils;
import ru.otus.java.hibernate.dao.UserDaoHibernate;
import ru.otus.java.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;
import java.util.Optional;

public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        long id = dbServiceUser.saveUser(new User(0, "Harry", new Address(0, "Diagon Alley"), List.of(new Phone(0, "681287888"))));
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        id = dbServiceUser.saveUser(new User(1L, "Ivan", new Address(1L,"Stroiteley"), List.of(new Phone(1L,"890887888"))));
        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        logger.info("-----------------------------------------------------------");
        logger.info(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}
