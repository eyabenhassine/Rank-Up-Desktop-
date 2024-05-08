package com.example.rankup.controllers;

import com.example.rankup.entities.Reclamation;
import com.example.rankup.services.ReclamationService;
import com.twilio.Twilio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterReclamation {


    @FXML
    private TextField NumTelTF;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField typeTF;
    private final ReclamationService rec = new ReclamationService();

    @FXML
    void ajouterReclamation(ActionEvent event) {

        // Check if any of the fields are empty
        if (nomTF.getText().isEmpty() || NumTelTF.getText().isEmpty() || typeTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || dateTF.getText().isEmpty()) {
            // Display an alert if any field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return; // Exit the method if any field is empty
        }

        // Check if NumTelTF contains only digits
        if (!NumTelTF.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Phone number must contain only digits");
            alert.showAndWait();
            return;
        }

        try {
            // Adds a new Reclamation object using data from text fields
            rec.add(new Reclamation(nomTF.getText(), Integer.parseInt(NumTelTF.getText()), typeTF.getText(), descriptionTF.getText(), dateTF.getText()));



            // Send SMS using Twilio
            String accountSid = "ACc9c627e21ad6900d0432e0f3eeddff1c";
            String authToken = "a0c2c5cad27d106577c065e112a7a8c3";
            String twilioNumber = "+14052469879";

            // Initialize Twilio
            Twilio.init(accountSid, authToken);

            // Send SMS
            Message message = Message.creator(
                            new PhoneNumber("+216" + NumTelTF.getText()),  // Replace with recipient's phone number
                            new PhoneNumber(twilioNumber),               // Your Twilio phone number
                            "Votre réclamation a été ajoutée avec succès. Merci!")  .create();
            showNotification("AJOUT réussie", "Reclamation ajoutée avec succès.");

            System.out.println("Message SID: " + message.getSid()); // Print SID for debugging purposes

        } catch (SQLException e) {
            // Displays an error alert if SQLException occurs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

}




    @FXML
    void naviguer(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamation.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void BackHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }
}
