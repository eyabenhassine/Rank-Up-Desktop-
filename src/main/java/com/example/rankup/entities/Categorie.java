package com.example.rankup.entities;


public class Categorie {
    private int id;
    private String nom_categorie;
    private String type;
    private String image;


    public Categorie(int id,String nom_categorie, String type ,String image) {
        this.id = id;
        this.nom_categorie = nom_categorie;
        this.type = type;
        this.image =image;

    }


    public Categorie(String nom_categorie, String type ,String image) {
        this.nom_categorie = nom_categorie;
        this.type = type;
        this.image = image;
    }

    public Categorie() {

    }

    public Categorie(String nomCategorie1, String type1) {
    }


    // Getters and setters for id, nom_categorie, and type


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        // Vérification du nom de la catégorie
        if (nom_categorie == null || nom_categorie.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom de catégorie invalide");
        }
        this.nom_categorie = nom_categorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        // Vérification du type
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type invalide");
        }
        this.type = type;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image == null || image.trim().isEmpty()) {
            throw new IllegalArgumentException("Chemin de l'image invalide");
        }
        this.image = image;
        }







    @Override
    public String toString() {
        return
                "Categorie{" +
                "id=" + id +
                ", nom_categorie='" + nom_categorie + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public boolean isValid() {
        return nom_categorie != null && !nom_categorie.isEmpty() && type != null && !type.isEmpty() && image != null && !image.isEmpty() ;
    }
}
