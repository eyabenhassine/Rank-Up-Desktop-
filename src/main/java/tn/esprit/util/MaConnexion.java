package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {

    //DB
    final String URL ="jdbc:mysql://localhost:3306/pidevsymfony";
    final String USR ="root";
    final String PWD ="";
    
    
    //att
    
    private Connection cnx;
    public static MaConnexion instance;
    
    //Constructor
    //Singleton step 1

    private MaConnexion() throws SQLException {
        try {
            cnx = DriverManager.getConnection(URL, USR, PWD);
            System.out.println("Connexion etablie avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() throws SQLException {
        if (instance == null)
            instance = new MaConnexion();
        return instance;
    }
}
