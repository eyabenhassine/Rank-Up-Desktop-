package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {

    private final String URL= "jdbc:mysql://localhost:3306/pidevsymfony";
    private final String USER= "root";
    private final String PSW= "";


    //att
    private Connection cnx;
    //singleton step2
   private static MaConnexion instance;


//constructor
//singleton step 1
    private MaConnexion(){
        try {
            cnx = DriverManager.getConnection(URL,USER,PSW);
            System.out.println("Connected Successfully!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//Getter


    public Connection getCnx() {
        return cnx;
    }

    //Singleton etape 3
    public static MaConnexion getInstance() {
        if(instance == null)
            instance = new MaConnexion();
        return instance;
    }
}
