package tn.esprit.entities;

public class Sponsor {
    private int id;
    private  String nom_sponsor,adresse_sponsor,mail_sponsor;


    // Constructor

    public Sponsor(int id, String nom_sponsor, String adresse_sponsor, String mail_sponsor) {
        this.id = id;
        this.nom_sponsor = nom_sponsor;
        this.adresse_sponsor = adresse_sponsor;
        this.mail_sponsor = mail_sponsor;
    }

    public Sponsor(String nom_sponsor, String adresse_sponsor, String mail_sponsor) {
        this.nom_sponsor = nom_sponsor;
        this.adresse_sponsor = adresse_sponsor;
        this.mail_sponsor = mail_sponsor;
    }

    public Sponsor() {

    }


    //Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_sponsor() {
        return nom_sponsor;
    }

    public void setNom_sponsor(String nom_sponsor) {
        this.nom_sponsor = nom_sponsor;
    }

    public String getAdresse_sponsor() {
        return adresse_sponsor;
    }

    public void setAdresse_sponsor(String adresse_sponsor) {
        this.adresse_sponsor = adresse_sponsor;
    }

    public String getMail_sponsor() {
        return mail_sponsor;
    }

    public void setMail_sponsor(String mail_sponsor) {
        this.mail_sponsor = mail_sponsor;
    }
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom_event='" + nom_sponsor + '\'' +
                ", adresse_sponsor=" + adresse_sponsor +
                ", mail_sponsor=" + mail_sponsor +
                '}';
    }
}
