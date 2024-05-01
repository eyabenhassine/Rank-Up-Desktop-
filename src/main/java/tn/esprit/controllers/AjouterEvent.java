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
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;
import java.time.LocalDate;
import org.controlsfx.control.Notifications;

import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterEvent {

    @FXML
    private DatePicker dateDebutField;

    @FXML
    private DatePicker dateFinField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nomEventField;

    @FXML
    private TextField typeField;
    private final EventService ev;

    {
        try {
            ev = new EventService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BackHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerlistEvent(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherEvent.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@FXML
    void ajouterEvent(ActionEvent event) {
        String nomEvent = nomEventField.getText();
        if (nomEvent == null || nomEvent.isEmpty()) {
            // Show an alert or provide a default value
            // For example:
            nomEvent = "Unnamed Event";
        }

        // Extract values from text fields
        String dateDebut = dateDebutField.getText();
        String dateFin = dateFinField.getText();
        String type = typeField.getText();
        String description = descriptionField.getText();

        // Check if any of the fields are empty
        if (dateDebut.isEmpty() || dateFin.isEmpty() || type.isEmpty() || description.isEmpty()) {
            // Show an alert to the user to fill in all the fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
            return; // Don't proceed with the insertion if any field is empty
        }

        // Insert the event into the database
        ev.insert(new Event(nomEvent, dateDebut, dateFin, type, description));
        // Optionally, show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Event added successfully.");
        alert.showAndWait();
    }*/

    /*@FXML
    void ajouterEvent(ActionEvent event) {
        String nomEvent = nomEventField.getText().trim();
        //LocalDate dateDebut = String.valueOf(dateDebutField.getValue());
        String dateDebut = String.valueOf(dateFinField.getValue());
        String dateFin = String.valueOf(dateFinField.getValue());
        String type = typeField.getText().trim();
        String description = descriptionField.getText().trim();
        System.out.println("le nom de l evenement est "+nomEvent);
        // Vérifier si le champ nomEvent est vide
        if (nomEvent.isEmpty()) {
            System.out.println("Le champ nomEvent est vide.");
            // Afficher une alerte à l'utilisateur pour indiquer que le champ nomEvent est obligatoire
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le champ Nom de l'événement est obligatoire.");
            alert.showAndWait();
            return; // Ne pas procéder à l'insertion si le champ nomEvent est vide
        }
        // Vérifier si l'un des champs est vide
        if ( dateDebut.isEmpty() || dateFin.isEmpty() || type.isEmpty() || description.isEmpty()) {
            // Afficher une alerte à l'utilisateur pour remplir tous les champs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Ne pas procéder à l'insertion si l'un des champs est vide
        }

        // Vérifier si la description contient au moins 12 caractères
        if (description.length() < 12) {
            // Afficher une alerte à l'utilisateur pour indiquer que la description est trop courte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La description doit contenir au moins 12 caractères.");
            alert.showAndWait();
            return; // Ne pas procéder à l'insertion si la description est trop courte
        }

        // Insérer l'événement dans la base de données
        ev.insert(new Event(nomEvent, dateDebut, dateFin, type, description));
        // Optionnel : afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Événement ajouté avec succès.");
        alert.showAndWait();
    }*/



    @FXML
    void ajouterEvent(ActionEvent event) {
        String nomEvent = nomEventField.getText().trim();
        LocalDate dateDebut = dateDebutField.getValue();
        LocalDate dateFin = dateFinField.getValue();
        String type = typeField.getText().trim();
        String description = descriptionField.getText().trim();
        LocalDate aujourdHui = LocalDate.now();

        System.out.println("le nom de l evenement est " + nomEvent);

        // Vérifier si le champ nomEvent est vide
        if (nomEvent.isEmpty()) {
            // Afficher une alerte à l'utilisateur pour indiquer que le champ nomEvent est obligatoire
            afficherAlerteErreur("Le champ Nom de l'événement est obligatoire.");
            return;
        }

        // Vérifier si l'un des champs est vide
        if (dateDebut == null || dateFin == null || type.isEmpty() || description.isEmpty()) {
            // Afficher une alerte à l'utilisateur pour remplir tous les champs
            afficherAlerteErreur("Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si la description contient au moins 12 caractères
        if (description.length() < 12) {
            // Afficher une alerte à l'utilisateur pour indiquer que la description est trop courte
            afficherAlerteErreur("La description doit contenir au moins 12 caractères.");
            return;
        }

        // Vérifier si la date de début est supérieure à la date d'aujourd'hui
        if (dateDebut.isBefore(aujourdHui)) {
            afficherAlerteErreur("La date de début doit être ultérieure à la date d'aujourd'hui.");
            return;
        }

        // Vérifier si la date de fin est après la date de début
        if (dateFin.isBefore(dateDebut)) {
            afficherAlerteErreur("La date de fin ne peut pas être avant la date de début.");
            return;
        }

        // Insérer l'événement dans la base de données
        ev.insert(new Event(nomEvent, dateDebut.toString(), dateFin.toString(), type, description));

        // Optionnel : afficher un message de succès
        afficherAlerteInformation("Événement ajouté avec succès.");
        showNotification("Opération réussie", "Evenement a été Ajouté avec succès.");

    }

    void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void afficherAlerteInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }




    /*@FXML
    void ajouterEvent(ActionEvent event){

        ev.insert(new Event(nomEventField.getText(),dateDebutField.getText(),dateFinField.getText(), typeField.getText(), descriptionField.getText()));
    }*/

    /*{
        try {
            ev = new EventService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void ajouterEvent(ActionEvent event) throws SQLException {
        ev.add(new Event(nomEventField.getText(),typeField.getText(),descriptionField.getText(),dateDebutField.getText(),dateFinField.getText()));
    }*/
} //Old Code*/




/*package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;

import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterEvent {

    @FXML
    private DatePicker dateDebutField;

    @FXML
    private DatePicker dateFinField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nomEventField;

    @FXML
    private TextField typeField;

    private final EventService ev;

    public AjouterEvent() {
        try {
            ev = new EventService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void ajouterEvent(ActionEvent event) {
                LocalDate dateDebut = dateDebutField.getValue();
                LocalDate dateFin = dateFinField.getValue();

                if (dateDebut == null || dateFin == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Champ obligatoire manquant");
                    alert.setContentText("Veuillez sélectionner une date de début et une date de fin.");
                    alert.showAndWait();
                } else {
            String dateDebutString = dateDebut.toString();
            String dateFinString = dateFin.toString();
            ev.add(new Event(nomEventField.getText(), typeField.getText(), descriptionField.getText(), dateDebutString, dateFinString));
        }
    }

}*/

