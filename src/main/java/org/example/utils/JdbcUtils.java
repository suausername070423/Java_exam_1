package org.example.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static Connection connection;
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try (InputStream input = JdbcUtils.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Fail to load DB: " + e.getMessage());
        }
    }

    public static Connection connect() throws Exception {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
            return connection;
        } catch (SQLException e) {
            throw new Exception("Connection fail: " + e.getMessage(), e);
        }
    }

    public static void disconnect() throws Exception {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected with database.");
            } else {
                System.out.println("Don't have any connection to close");
            }
        } catch (SQLException e) {
            throw new Exception("Error when disconnected with database.: " + e.getMessage(), e);
        }
    }
}
