package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Suivi_Reclamation;
import tn.esprit.services.Suivi_ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class UpdateSuiviReclamationController {

    @FXML private TextField idField;
    @FXML private TextField idRecField;
    @FXML private TextField statusField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker datePicker;

    private Suivi_ReclamationService service = new Suivi_ReclamationService();

    public void setSuiviReclamation(Suivi_Reclamation reclamation) {
        System.out.println("Setting reclamation data for update: " + reclamation);
        idField.setText(String.valueOf(reclamation.getId()));
        idRecField.setText(String.valueOf(reclamation.getIdRec()));
        statusField.setText(reclamation.getStatus());
        descriptionField.setText(reclamation.getDescription());
        datePicker.setValue(reclamation.getLocalDate()); // Ensure date is being set correctly
    }

    @FXML
    void updateSuiviReclamation(ActionEvent event) {
        try {
            // Vérifier si les champs sont vides
            if (idField.getText().isEmpty() || idRecField.getText().isEmpty() || statusField.getText().isEmpty() || descriptionField.getText().isEmpty() || datePicker.getValue() == null) {
                showAlert("Input Error", "Please fill in all fields");
                return; // Stopper l'exécution de la méthode
            }

            // Vérifier si les identifiants sont des nombres
            int id = Integer.parseInt(idField.getText());
            int idRec = Integer.parseInt(idRecField.getText());

            // Récupérer les valeurs des champs
            String status = statusField.getText();
            String description = descriptionField.getText();
            LocalDate date = datePicker.getValue();
            String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Préparer l'objet Suivi_Reclamation pour la mise à jour
            Suivi_Reclamation suiviReclamation = new Suivi_Reclamation(id, idRec, status, description, formattedDate);

            // Mettre à jour le suivi de réclamation
            service.update(suiviReclamation);

            // Afficher un message de succès
            showAlert("Success", "Suivi Reclamation updated successfully");

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for IDs");
        } catch (SQLException e) {
            showAlert("Database Error", "Error updating the database: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void naviguerExit(ActionEvent event) {
        try {
            System.out.println("Navigating back...");
            Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
            statusField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException("Error navigating back: " + e.getMessage(), e);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void BackToSuiviList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/suiviReclamationView.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
