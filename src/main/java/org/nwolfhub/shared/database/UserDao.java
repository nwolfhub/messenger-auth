package org.nwolfhub.shared.database;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.nwolfhub.messengerauth.config.DatabaseConfigurator;
import org.nwolfhub.shared.database.model.Dao;
import org.nwolfhub.shared.database.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class UserDao implements Dao {
    private HibernateController controller;

    public UserDao() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfigurator.class);
        //controller = new HibernateController((Properties) context.getBean("hibernateProperties"));
        controller = context.getBean(HibernateController.class);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Object get(Integer id) {
        Session session = controller.getSessionFactory().openSession();
        User obj = session.get(User.class, id);
        session.close();
        return obj;
    }
    public Object get (String username) {
        Session session = controller.getSessionFactory().openSession();
        Query query = session.createQuery("from User where username=:username")
                .setParameter("username", username);
        return query.uniqueResult();
    }

    public User getUser(Integer id) {
        return (User) get(id);
    }

    @Override
    public void save(Object obj) {
        Session session = controller.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object obj) {
        Session session = controller.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Object obj) {
        Session session = controller.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public List getAll() {return null;} //unimplemented
}
