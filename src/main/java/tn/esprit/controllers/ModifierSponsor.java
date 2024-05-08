package tn.esprit.controllers;

import org.controlsfx.control.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import tn.esprit.entities.Sponsor;
import tn.esprit.services.SponsorService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierSponsor implements Initializable {
    @FXML
    private TextField nomSponsorUpdate;
    @FXML
    private TextField adresseSponsorUpdate;
    @FXML
    private TextField mailSponsorUpdate;

    private SponsorService sponsorService;
    private Sponsor selectedSponsor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize your controller here. For example:
        if (selectedSponsor != null) {
            setSelectedSponsor(selectedSponsor);
        }
        try {
            sponsorService = new SponsorService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void modifierSponsor(ActionEvent event) {
        if (selectedSponsor != null) {
            try {
                String nomSponsor = nomSponsorUpdate.getText();
                String adresseSponsor = adresseSponsorUpdate.getText();
                String mailSponsor = mailSponsorUpdate.getText();

                // Check if mandatory fields are not empty
                if (nomSponsor.isEmpty() || adresseSponsor.isEmpty() || mailSponsor.isEmpty()) {
                    afficherAlerteErreur("Input Error", "Please fill in all fields.");
                    return;
                }

                // Update the properties of the selected sponsor
                selectedSponsor.setNom_sponsor(nomSponsor);
                selectedSponsor.setAdresse_sponsor(adresseSponsor);
                selectedSponsor.setMail_sponsor(mailSponsor);

                // Call the sponsor service's update method
                sponsorService.update(selectedSponsor);

                afficherAlerteInformation("Success", "Sponsor updated successfully!");
                showNotification("Operation Successful", "Sponsor has been updated successfully.");

            } catch (SQLException e) {
                afficherAlerteErreur("Error", "Failed to update sponsor: " + e.getMessage());
            }
        } else {
            afficherAlerteErreur("No Selection", "Please select a sponsor to update.");
        }
    }

    public void setSelectedSponsor(Sponsor sponsor) {
        this.selectedSponsor = sponsor;

        if (sponsor != null) {
            nomSponsorUpdate.setText(sponsor.getNom_sponsor());
            adresseSponsorUpdate.setText(sponsor.getAdresse_sponsor());
            mailSponsorUpdate.setText(sponsor.getMail_sponsor());




        }
    }

    @FXML
    void naviguerAjout(ActionEvent event) {
        // Implement navigation to add if necessary
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterSponsor.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherAlerteErreur("Error", "Failed to load add sponsor view.");
        }
    }

    @FXML
    void naviguerExit(ActionEvent event) {
        // Implement exit action if necessary
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherSponsor.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherAlerteErreur("Error", "Failed to load sponsor display view.");
        }
    }

    private void afficherAlerteErreur(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAlerteInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // You can customize the style here
                .show();
    }

    public void BackB(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherSponsor.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backhome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}