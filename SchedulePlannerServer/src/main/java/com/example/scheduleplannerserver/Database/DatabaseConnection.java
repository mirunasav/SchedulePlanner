package com.example.scheduleplannerserver.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";
    private static Connection connection = null;
    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        if (DatabaseConnection.connection == null)
            DatabaseConnection.createconnection();
        return DatabaseConnection.connection;
    }

    private static void createconnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Cannot connect to DB; " + e.toString());
        }
    }

    public static void closeConnection() {
        try {
            DatabaseConnection.connection.close();
        } catch (SQLException e) {
            System.out.println("error when closing DB! " + e.toString());
        }
    }
}
