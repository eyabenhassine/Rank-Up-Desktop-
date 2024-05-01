package tn.esprit.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DBConnection {
    final String URL = "jdbc:mysql://localhost:3306/rankup";
    final String USER = "root";
    final String PASSWORD = "";
    private Connection con;
    private static DBConnection instance;

//    java.sql.Connection con = null;

    public DBConnection() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DB Connected.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    public Connection getConnection() {
        return con;
    }
}
