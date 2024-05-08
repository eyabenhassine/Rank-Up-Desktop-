package org.example.rankup.entities;

public class lecon {
    private int id;
    private String nom_lecon;
    private String url;
    private String description;
    private int prix;

    public lecon() {
    }

    public lecon(int id,String nom_lecon, String url, int prix, String description) {
        this.id = id;
        this.nom_lecon = nom_lecon;
        this.url = url;
        this.prix = prix;
        this.description = description;
    }
    public lecon(String nom_lecon, String url, int prix, String description) {
        this.nom_lecon = nom_lecon;
        this.url = url;
        this.prix = prix;
        this.description = description;
    }
    // Getters and setters for id, nom_lecon, url, description, prix

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_lecon() {
        return nom_lecon;
    }

    public void setNom_lecon(String nom_lecon) {
        // Vérification du nom de la leçon
        if (nom_lecon == null || nom_lecon.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom de leçon invalide");
        }
        this.nom_lecon = nom_lecon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        // Vérification du nom de la leçon
        if (url== null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("url invalide");
        }
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // Vérification de la description
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description invalide");
        }
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        // Vérification du prix
        if (prix <= 0) {
            throw new IllegalArgumentException("Prix invalide");
        }
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "lecon{" +
                "id=" + id +
                ", nom_lecon='" + nom_lecon + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                '}';
    }
    public boolean isValid() {
        return nom_lecon != null && !nom_lecon.isEmpty() &&
                url != null && !url.isEmpty() &&
                prix > 0 && description != null && !description.isEmpty();
    }
}
