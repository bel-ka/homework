package ru.otus.java.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.model.User;

@Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory buildSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        MetadataSources metadataSources = new MetadataSources(createServiceRegistry(configuration));
        metadataSources.addAnnotatedClass(User.class);

        Metadata metadata = metadataSources.getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }

    private static StandardServiceRegistry createServiceRegistry(org.hibernate.cfg.Configuration configuration) {
        return new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
    }
}
