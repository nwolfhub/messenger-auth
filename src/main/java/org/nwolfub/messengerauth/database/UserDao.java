package org.nwolfub.messengerauth.database;

import org.hibernate.Session;
import org.nwolfub.messengerauth.config.DatabaseConfigurator;
import org.nwolfub.messengerauth.database.model.Dao;
import org.nwolfub.messengerauth.database.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Properties;

public class UserDao implements Dao {
    private HibernateController controller;

    public UserDao() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfigurator.class);
        //controller = new HibernateController((Properties) context.getBean("hibernateProperties"));
        controller = context.getBean("hibernate", HibernateController.class);
    }
    @Override
    public Object get(Integer id) {
        Session session = controller.getSessionFactory().openSession();
        User obj = session.get(User.class, id);
        session.close();
        return obj;
    }

    public User getUser(Integer id) {
        return (User) get(id);
    }

    @Override
    public void save(Object obj) {
        Session session = controller.getSessionFactory().openSession();
        session.save(obj);
        session.close();
    }

    @Override
    public void update(Object obj) {
        Session session = controller.getSessionFactory().openSession();
        session.update(obj);
        session.close();
    }

    @Override
    public void delete(Object obj) {
        Session session = controller.getSessionFactory().openSession();
        session.delete(obj);
        session.close();
    }

    @Override
    public List getAll() {return null;} //unimplemented
}
