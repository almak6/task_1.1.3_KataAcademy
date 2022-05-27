package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser("Ivan", "Fadew", (byte) 35);
        userServiceImpl.saveUser("Alex", "Max", (byte) 30);
        userServiceImpl.saveUser("Nicolas", "Sarkozy", (byte) 60);
        userServiceImpl.saveUser("Karina", "Kambarova", (byte) 28);
        userServiceImpl.getAllUsers().forEach(System.out::println);
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
