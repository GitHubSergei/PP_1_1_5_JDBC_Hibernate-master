package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/task_1_1_4_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    public static Connection getConnection() /*throws SQLException */{
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (ClassNotFoundException | SQLException ex) {
                //throw new SQLException("Unable to connect");
                ex.printStackTrace();
            }
        }
        return connection;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
