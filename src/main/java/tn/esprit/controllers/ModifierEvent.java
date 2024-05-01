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
import org.controlsfx.control.Notifications;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierEvent implements Initializable {
    @FXML
    private TextField nomEventUpdate;
    @FXML
    private TextField dateDebutUpdate;
    @FXML
    private TextField dateFinUpdate;
    @FXML
    private TextField typeUpdate;
    @FXML
    private TextField descriptionUpdate;

    private EventService eventService;
    private Event selectedEvent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser votre contrôleur ici. Par exemple :
        if (selectedEvent != null) {
            setSelectedEvent(selectedEvent);
        }
        try {
            eventService = new EventService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;

        if (event != null) {
            nomEventUpdate.setText(event.getNom_event());
            dateDebutUpdate.setText(event.getDate_debut());
            dateFinUpdate.setText(event.getDate_fin());
            typeUpdate.setText(event.getType());
            descriptionUpdate.setText(event.getDescription());
        }
    }

    @FXML
    void modifierEvent(ActionEvent event) {
        if (selectedEvent != null) {
            try {
                String nomEvent = nomEventUpdate.getText();
                String dateDebut = dateDebutUpdate.getText();
                String dateFin = dateFinUpdate.getText();
                String type = typeUpdate.getText();
                String description = descriptionUpdate.getText();

                // Vérifier que les champs obligatoires ne sont pas vides
                if (nomEvent.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty() || type.isEmpty() || description.isEmpty()) {
                    afficherAlerteErreur("Erreur de saisie", "Veuillez remplir tous les champs.");
                    return;
                }

                // Mettre à jour les propriétés de l'événement sélectionné
                selectedEvent.setNom_event(nomEvent);
                selectedEvent.setDate_debut(dateDebut);
                selectedEvent.setDate_fin(dateFin);
                selectedEvent.setType(type);
                selectedEvent.setDescription(description);

                // Appeler la méthode de mise à jour de l'événement dans le service
                eventService.update(selectedEvent);

                afficherAlerteInformation("Modification réussie", "L'événement a été modifié avec succès !");
                showNotification("Opération réussie", "Evenement a été modifié avec succès.");

            } catch (SQLException e) {
                afficherAlerteErreur("Erreur", "Erreur lors de la modification de l'événement : " + e.getMessage());
            }
        } else {
            afficherAlerteErreur("Aucune sélection", "Veuillez sélectionner un événement à modifier.");
        }
    }

    @FXML
    void naviguerAjout(ActionEvent event) {
        // Implémentez la navigation vers l'ajout si nécessaire
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterEvent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherAlerteErreur("Erreur", "Impossible de charger la vue d'ajout d'événement.");
        }
    }

    @FXML
    void naviguerExit(ActionEvent event) {
        // Implémentez l'action de sortie si nécessaire
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherAlerteErreur("Erreur", "Impossible de charger la vue d'affichage des événements.");
        }
    }

    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAlerteInformation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
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
}
