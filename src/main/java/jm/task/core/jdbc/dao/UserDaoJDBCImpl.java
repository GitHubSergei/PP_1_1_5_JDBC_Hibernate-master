package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = new Util().getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users ("+
                    "id int PRIMARY KEY AUTO_INCREMENT,"+
                    "name VARCHAR(50) NOT NULL, lastname VARCHAR(50) NOT NULL, age TINYINT NOT NULL)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement statement = new Util().getConnection().createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES ( ?, ?, ?);";
        try (PreparedStatement preparedStatement = new Util().getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?;";
        try (PreparedStatement preparedStatement = new Util().getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ResultSet resultSet;
        List<User> users = new ArrayList<>();
        try (Statement statement = new Util().getConnection().createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM users");
            if (resultSet != null) {
                while (resultSet.next()) {
                    users.add(new User(resultSet.getString("name"),
                            resultSet.getString("lastname"),
                            resultSet.getByte("age")));
                    users.get(users.size()-1).setId(resultSet.getLong("id"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = new Util().getConnection().createStatement()) {
            statement.execute("DELETE FROM users WHERE id;");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


