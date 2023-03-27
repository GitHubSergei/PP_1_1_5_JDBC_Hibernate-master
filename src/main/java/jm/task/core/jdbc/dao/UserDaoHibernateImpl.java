package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/*
Rollback сделал везде, кроме getAllUsers - полагаю, что там откатывать нечего будет, если возникнет ошибка.

 */

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                        "id int PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL, age TINYINT NOT NULL);").executeUpdate();
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.delete(session.get(User.class, id));
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createQuery("delete from User").executeUpdate();
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }
    }
}
