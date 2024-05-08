package com.example.rankup.controllers;

import com.example.rankup.entities.Reclamation;
import com.example.rankup.entities.User;
import com.example.rankup.services.ReclamationService;
import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import com.twilio.Twilio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AjouterReclamation {


    @FXML
    private TextField NumTelTF;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private Text nomTF;

    private UserService userService = new UserService();
    private User CurrUser = null;

    @FXML
    private TextField typeTF;
    private final ReclamationService rec = new ReclamationService();

    @FXML
    public void initialize() {
        System.out.println("Current session is: ");
        System.out.println(SessionManager.getSession("userId"));
        int userId = (int) SessionManager.getSession("userId");
        System.out.println(userId);

        CurrUser = userService.getOneByID(userId);
        System.out.println(CurrUser);
        nomTF.setText(CurrUser.getUsername());
    }

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
            rec.add(new Reclamation(nomTF.getText(), Integer.parseInt(NumTelTF.getText()), typeTF.getText(), descriptionTF.getText(), dateTF.getText(), CurrUser.getId()));



            // Send SMS using Twilio
            String accountSid = "ACaf2ebced6cc47cb1c63a40515fba8c2a";
            String authToken = "6756c878dd1a611c7d33872f5a8185ec";
            String twilioNumber = "+15597084928";

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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/afficherReclamation.fxml")));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void BackHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home.fxml")));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    void buttontest(ActionEvent event) {

    }
}
