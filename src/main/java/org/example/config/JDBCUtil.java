package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    static Connection conn = null;

    private static final String URL = "jdbc:mysql://localhost:3306/kb7-jdbc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    static {
        try {
            conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}

