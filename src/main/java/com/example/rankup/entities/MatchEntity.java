package com.example.rankup.entities;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MatchEntity {
    private String id;
    private LocalDate date_debut;
    private List<Equipe> equipes = new ArrayList<>();

    public MatchEntity() {
        this.date_debut = LocalDate.of(1, Month.JANUARY, 1);
        this.id = UUID.randomUUID().toString();
    }

    public MatchEntity(LocalDate date_debut) {
        this.date_debut = date_debut;
        this.id = UUID.randomUUID().toString();
    }

    public MatchEntity(String id, LocalDate dateDebut) {
        this.id = id;
        date_debut = dateDebut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                '}';
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }
}

