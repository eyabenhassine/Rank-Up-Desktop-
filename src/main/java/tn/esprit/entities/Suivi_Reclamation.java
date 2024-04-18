package tn.esprit.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Suivi_Reclamation {
    private  int id ;
    private  int idRec ;
    private  String status;
    private  String description ;
    private  String date ;

    public Suivi_Reclamation() {
    }
    public Suivi_Reclamation(int id, int idRec,String status, String description,  String date) {
        this.id = id;
        this.idRec = idRec;
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


    public  int getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
    }

    public  int getIdRec() {
        return idRec;
    }

    public  void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public  String getStatus() {
        return status;
    }

    public  void setStatus(String status) {
        this.status = status;
    }

    public  String getDescription() {
        return description;
    }

    public  void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }
    public LocalDate getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
    public  void setDate(String date) {
        this.date = date;
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
