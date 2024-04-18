package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
            System.out.println("Updating reclamation...");
            int id = Integer.parseInt(idField.getText());
            int idRec = Integer.parseInt(idRecField.getText());
            String status = statusField.getText();
            String description = descriptionField.getText();
            LocalDate date = datePicker.getValue();
            String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            Suivi_Reclamation suiviReclamation = new Suivi_Reclamation(id, idRec, status, description, formattedDate);
            System.out.println("Prepared object for update: " + suiviReclamation);
            service.update(suiviReclamation);

            showAlert("Success", "Suivi Reclamation updated successfully");
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for Reclamation ID");
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
            Parent root = FXMLLoader.load(getClass().getResource("/suiviReclamationView.fxml"));
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
}
