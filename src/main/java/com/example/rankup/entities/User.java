package com.example.rankup.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.*;

public class User {
    int id;
    String equipe_id;
    String email, firstname, lastname, username, resetToken, password, photo, phone, whyBlocked, status, bio, summonername;
    LocalDate birthdate;
    List<String> roles = new ArrayList<>();
    String elo ;

    public User() {
    }

    public User(String firstname, String lastname, String username, String phone, String bio, String summonername, LocalDate birthdate, String elo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phone = phone;
        this.bio = bio;
        this.summonername = summonername;
        this.birthdate = birthdate;
        this.elo = elo;
    }

    public User(int id, String email, String username, String password, LocalDate birthdate, List<String> roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
        this.roles = roles;
    }
    public User(String email, String username, String password, LocalDate birthdate, List<String> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
        this.roles = roles;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getEquipe_id() {
        return equipe_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEquipe_id(String id) {
        this.equipe_id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWhyBlocked() {
        return whyBlocked;
    }

    public void setWhyBlocked(String whyBlocked) {
        this.whyBlocked = whyBlocked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSummonername() {
        return summonername;
    }

    public void setSummonername(String summonername) {
        this.summonername = summonername;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getElo() {
        return elo;
    }

    public void setElo(String elo) {
        this.elo = elo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
