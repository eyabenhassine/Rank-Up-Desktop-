package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.entities.SuiviReclamation;
import tn.esprit.services.SuiviReclamationService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjouterSuiviReclamationController {

    @FXML
    private TextField idRecField;
    @FXML
    private TextField statusField;
    @FXML
    private TextField descriptionField;
    @FXML
    private DatePicker datePicker;
    private SuiviReclamationService service = new SuiviReclamationService();

    @FXML
    void ajouterSuiviReclamation(ActionEvent event) {
       /* try {
            // Vérifier si les champs sont vides
            if (idRecField.getText().isEmpty() || statusField.getText().isEmpty() || descriptionField.getText().isEmpty() || datePicker.getValue() == null) {
                showAlert("Input Error", "Please fill in all fields");
                return; // Stopper l'exécution de la méthode
            }

            // Vérifier si l'ID de réclamation est un nombre
            int idRec = Integer.parseInt(idRecField.getText());

            // Récupérer les valeurs des champs
            String status = statusField.getText();
            String description = descriptionField.getText();
            LocalDate date = datePicker.getValue();
            String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Ajouter le suivi de réclamation
            SuiviReclamation suiviReclamation = new SuiviReclamation( status, description, formattedDate);
            service.add(suiviReclamation);

            // Afficher un message de succès
            showAlert("Success", "Suivi Reclamation added successfully");

            // Effacer les champs après l'ajout
            idRecField.clear();
            statusField.clear();
            descriptionField.clear();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for Reclamation ID");
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/suiviReclamationView.fxml"));
            idRecField.getScene().setRoot(root);
        } catch (IOException e) {
            showAlert("Loading Error", "Could not load the view: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    } */
    }
}
