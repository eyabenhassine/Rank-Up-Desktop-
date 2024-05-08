package tn.esprit.entities;

import java.util.Date;

public class SuiviReclamation {
    private  int id ;
    private  String status;
    private  String description ;
    private Date date ;
    //cle etranger
    Reclamation reclamation ;
    public SuiviReclamation() {
    }

    public SuiviReclamation(int id, String status, String description, Date date, Reclamation reclamation) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.date = date;
        this.reclamation = reclamation;
    }


    public SuiviReclamation(String status, String description, Date date, Reclamation reclamation) {
        this.status = status;
        this.description = description;
        this.date = date;
        this.reclamation = reclamation;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public  int getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Suivi_Reclamation{" +
                "status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", reclamation=" + reclamation +
                '}';
    }




}
