package com.example.rankup.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    int id;
    String email, firstname, lastname, username, resetToken, password, photo, phone, whyBlocked, status, bio, summonername;
    LocalDate birthdate;
    List<String> roles = new ArrayList<>();
    List<String> elo = new ArrayList<>();

    public User() {
    }

    public User(int id, String email, String username, String password, LocalDate birthdate) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
    }

    public User( String email, String username, String password, LocalDate birthdate) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
    }
    public User(int id, String email, String firstname, String lastname, String username, String resetToken, String password, String photo, String phone, String whyBlocked, String status, String bio, String summonername, LocalDate birthdate, List<String> roles, List<String> elo) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.resetToken = resetToken;
        this.password = password;
        this.photo = photo;
        this.phone = phone;
        this.whyBlocked = whyBlocked;
        this.status = status;
        this.bio = bio;
        this.summonername = summonername;
        this.birthdate = birthdate;
        this.roles = roles;
        this.elo = elo;
    }

    public User(String email, String firstname, String lastname, String username, String resetToken, String password, String photo, String phone, String whyBlocked, String status, String bio, String summonername, LocalDate birthdate, List<String> roles, List<String> elo) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.resetToken = resetToken;
        this.password = password;
        this.photo = photo;
        this.phone = phone;
        this.whyBlocked = whyBlocked;
        this.status = status;
        this.bio = bio;
        this.summonername = summonername;
        this.birthdate = birthdate;
        this.roles = roles;
        this.elo = elo;
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

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getElo() {
        return elo;
    }

    public void setElo(List<String> elo) {
        this.elo = elo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
