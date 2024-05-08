package com.example.rankup.entities;

import java.util.Date;
public class Reservation {
    int id;
    Date date;
    String description;

    public Reservation() {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public Reservation(Date date, String description) {
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
