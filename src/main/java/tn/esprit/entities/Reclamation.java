package tn.esprit.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Reclamation {
    //att
    private static int id ;
    private static String nom;
    private static int NumTel ;
    private static String type;
    private static String description ;
    private static String date ;

    //constructor


    public Reclamation() {
    }

    public Reclamation(int id, String nom, int NumTel, String type, String description, String date) {
        this.id = id;
        this.nom=nom;
        this.NumTel=NumTel;
        this.type = type;
        this.description = description;
        this. date =  date;
    }
    public Reclamation( String nom,int NumTel,String type, String description,  String date) {
        this.nom=nom;
        this.NumTel=NumTel;
        this.type = type;
        this.description = description;
        this. date =  date;
    }

  /* public Reclamation(String text, int numTel, String text1, String text2, String text3) {
    }*/


//GETTERS And SETTERS


    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Reclamation.date = date;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        Reclamation.nom = nom;
    }

    public static int getNumTel() {
        return NumTel;
    }

    public static void setNumTel(int numTel) {
        NumTel = numTel;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", NumTel='" + NumTel + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}

