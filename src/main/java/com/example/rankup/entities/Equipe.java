package com.example.rankup.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Equipe {
    private String id;
    private String match_id;
    String nom;
    private List<User> users = new ArrayList<>();

    public Equipe() {
        this.id = UUID.randomUUID().toString();
    }

    public Equipe(String nom) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
    }

    public Equipe(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public <E> List<E> getUsers() {
        return (List<E>) users;
    }

    @Override
    public String toString() {
        String result = this.nom+": ";
        for (User user : users) {
            result += user.toString()+" ";
        }
        return result;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}
