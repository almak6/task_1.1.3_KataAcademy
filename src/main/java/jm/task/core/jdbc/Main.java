package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;




public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();
        userDao.saveUser("Ivan", "Fadew", (byte) 35);
        userDao.saveUser("Alex", "Max", (byte) 30);
        userDao.saveUser("Nicolas", "Sarkozy", (byte) 60);
        userDao.saveUser("Karina", "Kambarova", (byte) 28);
        userDao.getAllUsers().forEach(System.out::println);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
