package com.example.rankup.entities;

public class Equipe {
    private int idCircuit;
    String nom;

    public Equipe(String nom) {
        this.nom = nom;
    }

    public Equipe() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
