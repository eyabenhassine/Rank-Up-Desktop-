package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ReclamationService;

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
}
