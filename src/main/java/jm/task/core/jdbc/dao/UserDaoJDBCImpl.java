package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;


    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS user" + //проверка на наличие такой таблицы
                            "(" +
                            "id BIGINT PRIMARY KEY AUTO_INCREMENT," + //эквивал long, первичный уникальный ключ, инкрементирующееся
                            "name VARCHAR(30) NOT NULL," + //не может быть null
                            "last_name VARCHAR(30) NOT NULL," + //не может быть null
                            "age TINYINT NOT NULL" + //-128 127 - эквивал byte, не может быть null
                            ");");
        } catch (SQLException ex) {
            ex.getStackTrace();
            throw new RuntimeException("Не удалось выполнить запрос к БД.", ex);
        }
    }


    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS user");
        } catch (SQLException ex) {
            throw new RuntimeException("Не удалось выполнить запрос к БД.", ex);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO user(name, last_name, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Не удалось выполнить запрос к БД.", ex);
        }
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Не удалось выполнить запрос к БД.", ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                result.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Не удалось выполнить запрос к БД.", ex);
        }
        return result;
    }


    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException ex) {
            throw new RuntimeException("Не удалось выполнить запрос к БД.", ex);
        }
    }
}
