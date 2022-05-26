package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS user" +
                "(" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(30) NOT NULL," +
                "last_name VARCHAR(30) NOT NULL," +
                "age TINYINT NOT NULL" +
                ");";
        try(Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.createNativeQuery("DROP TABLE IF EXISTS user", User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            User userFromDb = session.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id).getSingleResult();
            session.remove(userFromDb);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try(Session session = getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).getResultList();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        }  catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }
}
