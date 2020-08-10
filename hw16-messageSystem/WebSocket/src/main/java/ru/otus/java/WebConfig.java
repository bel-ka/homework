package ru.otus.java;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.otus.java.model.User;
import ru.otus.java.services.DBServiceUser;
import ru.otus.java.services.FrontendService;
import ru.otus.java.services.FrontendServiceImpl;
import ru.otus.java.services.handlers.GetUserDataRequestHandler;
import ru.otus.java.services.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Configuration
@ComponentScan
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

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

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public CallbackRegistry callbackRegistry(){
      return new CallbackRegistryImpl();
    }

    @Bean(name = "databaseMsClient")
    public MsClient databaseMsClient(MessageSystem messageSystem, DBServiceUser dbServiceUser, CallbackRegistry callbackRegistry) {
        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceUser));
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean(name = "frontendMsClient")
    public MsClient frontendMsClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore responseHandlerFrontendStore = new HandlersStoreImpl();
        responseHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, responseHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(frontendMsClient);

        return frontendMsClient;
    }

    @Bean
    public FrontendService frontendService(MsClient frontendMsClient){
        return new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    }
}
