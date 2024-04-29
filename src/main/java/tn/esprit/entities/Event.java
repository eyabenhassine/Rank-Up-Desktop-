package tn.esprit.entities;

public class Event {

    //Att
    private int id;
    private String nom_event,date_debut,date_fin,type,description;


    //constructor


    /*public Event(String nom_event, String date_debut, String date_fin, String type, String description) {
        this.nom_event = nom_event;

        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.type = type;
        this.description = description;
    }*/

    public Event(int id, String nom_event, String date_debut, String date_fin, String type, String description) {
        this.id = id;
        this.nom_event = nom_event;

        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.type = type;
        this.description = description;
    }

    public Event() {


    }

    public Event(String nom_event, String date_debut, String date_fin, String type, String description) {
        this.nom_event = nom_event;

        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.type = type;
        this.description = description;
    }


    //Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }



    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Display

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom_event='" + nom_event + '\'' +

                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", type='" + type + '\'' +
                ", description=" + description +
                '}';
    }
}
