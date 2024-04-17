package tn.esprit.entities;

import java.time.LocalDate;

public class Suivi_Reclamation {
    private static int id ;
    private static int idRec ;
    private static String status;
    private static String description ;
    private static String date ;

    public Suivi_Reclamation() {
    }
    public Suivi_Reclamation(int id, int idRec,String status, String description,  String date) {
        this.id = id;
        this.id = idRec;
        this.status = status;
        this.description = description;
        this. date =  date;
    }
    public Suivi_Reclamation( int idRec,String status, String description,  String date) {

        this.id = idRec;
        this.status = status;
        this.description = description;
        this. date =  date;
    }
//Getters and Setters


    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Suivi_Reclamation.id = id;
    }

    public static int getIdRec() {
        return idRec;
    }

    public static void setIdRec(int idRec) {
        Suivi_Reclamation.idRec = idRec;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Suivi_Reclamation.status = status;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        Suivi_Reclamation.description = description;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Suivi_Reclamation.date = date;
    }

    //display


    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id + ", idRec='" + idRec + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
