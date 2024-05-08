package com.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.rankup.services.SponsorService;
import com.example.rankup.entities.Sponsor;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterSponsor {

    @FXML
    private TextField NomSponsorField;

    @FXML
    private TextField AdresseSponsorField;

    @FXML
    private TextField MailSponsorField;

    private final SponsorService sponsorService;

    public AjouterSponsor() {
        try {
            sponsorService = new SponsorService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void AjouterSponsor(ActionEvent event) {
        String nomSponsor = NomSponsorField.getText().trim();
        String adresseSponsor = AdresseSponsorField.getText().trim();
        String mailSponsor = MailSponsorField.getText().trim();

        // Show a notification or confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sponsor Added");
        alert.setHeaderText(null);
        alert.setContentText("Sponsor added successfully!");
        alert.show();

        // Insérer le sponsor dans la base de données
        Sponsor sponsor = new Sponsor(nomSponsor, adresseSponsor, mailSponsor);
        sponsorService.add(sponsor);
    }

    @FXML
    public void BackHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void naviguerlistSponsor(ActionEvent event) {
        // Code to navigate to list of sponsors page
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherSponsor.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
