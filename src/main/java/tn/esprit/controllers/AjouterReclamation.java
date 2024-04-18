package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ReclamationService;

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

        try {
            rec.add(new Reclamation(nomTF.getText(),Integer.parseInt(NumTelTF.getText()),typeTF.getText(),descriptionTF.getText(),dateTF.getText()));
        } catch (SQLException e) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("error");
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
}
