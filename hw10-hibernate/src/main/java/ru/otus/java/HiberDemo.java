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

public class HiberDemo {
    private static final Logger logger = LoggerFactory.getLogger(HiberDemo.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        // user create
        User user1 = new User();
        user1.setName("Harry");
        Address addressU1 = new Address();
        addressU1.setStreet("Diagon Alley");
        user1.setAddress(addressU1);

        Phone phoneU1 = new Phone();
        phoneU1.setNumber("681287888");
        phoneU1.setUser(user1);

        user1.setPhones(List.of(phoneU1));

        long id = dbServiceUser.saveUser(user1);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        // user update
        User user2 = new User();
        user2.setName("Ivan");
        Address addressU2 = new Address();
        addressU2.setStreet("Stroiteley");
        user2.setAddress(addressU2);

        Phone phoneU2 = new Phone();
        phoneU2.setNumber("890887888");
        phoneU2.setUser(user2);

        user2.setPhones(List.of(phoneU2));

        id = dbServiceUser.saveUser(user2);
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
