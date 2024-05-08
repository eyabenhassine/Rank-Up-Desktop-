package org.example.utile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class maConnexion {
    // Les informations de connexion à la base de données
   final String URL = "jdbc:mysql://localhost:3306/lecon";
   final String UTILISATEUR = "root";
   final String MOT_DE_PASSE = "";

    private Connection cnx;

    static maConnexion instance;

    // Méthode pour établir une connexion à la base de données
    public maConnexion(){
        try {
           cnx = DriverManager.getConnection(URL,UTILISATEUR,MOT_DE_PASSE);
            System.out.println("connexion etablie");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static maConnexion getInstance() {
        if( instance == null)
            instance= new maConnexion();
        return instance;
    }
}
