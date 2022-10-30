package org.nwolfub.messengerauth.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.nwolfub.messengerauth.database.model.User;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class HibernateController {
    private SessionFactory sessionFactory;
    private Properties properties;

    public HibernateController(Properties properties) {
        this.properties = properties;
    }


    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);
                configuration.setProperties(properties);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
        return sessionFactory;
    }
}
