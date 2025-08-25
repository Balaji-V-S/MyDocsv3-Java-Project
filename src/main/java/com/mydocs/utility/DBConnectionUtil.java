package com.mydocs.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    private static String username = "root";
    private static String password = "root";
    //private static String url = "jdbc:postgresql://localhost:5432/scbdb";
    private static String url = "jdbc:mysql://localhost:3306/mydocs";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

