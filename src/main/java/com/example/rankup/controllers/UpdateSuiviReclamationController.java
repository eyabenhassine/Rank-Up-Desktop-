package com.example.rankup.controllers;

import com.example.rankup.entities.Reclamation;
import com.example.rankup.entities.SuiviReclamation;
import com.example.rankup.services.ReclamationService;
import com.example.rankup.services.SuiviReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateSuiviReclamationController {
   SuiviReclamationService suiviReclamationService = new SuiviReclamationService();
   SuiviReclamation suiviReclamationSelectionee = new SuiviReclamation();
    @FXML private TextField statusField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker datePicker;
    @FXML
    private ComboBox<Reclamation> reclamationComboBox;

    ReclamationService reclamationService = new ReclamationService();
    int idSuiviSelectione ;
    public void initialize() {
       

   
        // Remplir la ComboBox avec les réclamations
        populateReclamations();
    }
    private void populateReclamations() {
        try {
            // Récupérer toutes les réclamations
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(reclamationService.show());
            // Ajouter les réclamations à la ComboBox
            reclamationComboBox.setItems(reclamations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSuivi(SuiviReclamation suiviReclamation , int idSuivi) {
        this.suiviReclamationSelectionee = suiviReclamation;
        this.idSuiviSelectione = idSuivi ;
        if (suiviReclamationSelectionee != null) {
            statusField.setText(suiviReclamation.getStatus());
            descriptionField.setText(suiviReclamation.getDescription());
            java.sql.Date sqlDate = (Date) suiviReclamation.getDate();
            LocalDate localDate = sqlDate.toLocalDate();
            datePicker.setValue(localDate);        }
    }


    @FXML
    public void updateSuiviReclamation(ActionEvent actionEvent) {
        // Récupérer les valeurs des champs depuis l'interface
        String status = statusField.getText();
        String description = descriptionField.getText();
        LocalDate date = datePicker.getValue();

        // Créer un nouvel objet SuiviReclamation
        SuiviReclamation suiviReclamation = new SuiviReclamation();
        suiviReclamation.setId(idSuiviSelectione); // Utiliser l'ID de la SuiviReclamation sélectionnée

        Reclamation selectedReclamation = reclamationComboBox.getValue();
        suiviReclamation.setReclamation(selectedReclamation);
        suiviReclamation.setStatus(status);
        suiviReclamation.setDescription(description);
        suiviReclamation.setDate(java.sql.Date.valueOf(date));

        try {
            // Appeler la méthode update de SuiviReclamationService
            suiviReclamationService.update(suiviReclamation);
            // Afficher un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Suivi de réclamation mis à jour avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Afficher un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur lors de la mise à jour du suivi de réclamation!");
        }
        System.out.println(suiviReclamation);
    }


    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void naviguerExit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BackToSuiviList(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/suiviReclamationView.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void listSuivi(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/suiviReclamationView.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  private SuiviReclamationService service = new SuiviReclamationService();

    public void setSuiviReclamation(SuiviReclamation reclamation) {
        System.out.println("Setting reclamation data for update: " + reclamation);
        idField.setText(String.valueOf(reclamation.getId()));
        idRecField.setText(String.valueOf(reclamation.getIdRec()));
        statusField.setText(reclamation.getStatus());
        descriptionField.setText(reclamation.getDescription());
        datePicker.setValue(reclamation.getLocalDate()); // Ensure date is being set correctly
    }



    @FXML
    void naviguerExit(ActionEvent event) {

    }


    @FXML
    void BackToSuiviList(ActionEvent event) {

    } */
















}
