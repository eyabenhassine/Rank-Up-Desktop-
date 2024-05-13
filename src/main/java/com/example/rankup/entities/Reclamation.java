package com.example.rankup.entities;

public class Reclamation {
    //att
    private  int id ;
    private  String nom;
    private  Integer Num_Tel ;
    private  String type;
    private  String description ;
    private  String date ;
    private int userId;

    //constructor


    public Reclamation(int id) {
        this.id = id;
    }

    public Reclamation() {
    }

    public Reclamation(int id, Integer Num_Tel, String type, String description, String date, int userId) {
        this.id = id;
        this.nom = "user";
        this.Num_Tel = Num_Tel;
        this.type = type;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public Reclamation(int id, String nom, Integer Num_Tel, String type, String description, String date) {
        this.id = id;
        this.nom = nom;
        this.Num_Tel = Num_Tel;
        this.type = type;
        this.description = description;
        this.date = date;
    }
    public Reclamation( String nom,Integer Num_Tel,String type, String description,  String date) {
        this.nom=nom;
        this.Num_Tel=Num_Tel;
        this.type = type;
        this.description = description;
        this. date =  date;
    }

    public Reclamation(String nom, Integer Num_Tel, String type, String description, String date, int userId) {
        this.id = id;
        this.nom = "user";
        this.Num_Tel = Num_Tel;
        this.type = type;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public Reclamation(String text, String text1, String text2, String text3, String text4) {

    }

  /* public Reclamation(String text, int numTel, String text1, String text2, String text3) {
    }*/


//GETTERS And SETTERS


    public  void add(Reclamation rec) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  String getDate() {
        return date;
    }

    public void setDate(String date) {
       this.date = date;
    }

    public  String getNom() {
        return nom;
    }

    public  void setNom(String nom) {
        this.nom = nom;
    }

    public  Integer getNum_Tel() {
        return Num_Tel;
    }

    public  void setNum_Tel(int num_Tel) {
        Num_Tel = num_Tel;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                " nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

