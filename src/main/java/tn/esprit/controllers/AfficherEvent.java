package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Event;
import javafx.scene.control.TableView;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class AfficherEvent {
    @FXML
    private TableColumn<Event, String> DescriptionCol;

    @FXML
    private TableColumn<Event, String>  EndDateCol;

    @FXML
    private TableColumn<Event, String>  NomECol;

    @FXML
    private TableColumn<Event, String>  StartDateCol;

    @FXML
    private TableColumn<Event, String>  TypeCol;

    @FXML
    private TableView<Event> tableView;

    private final EventService rec;

    {
        try {
            rec = new EventService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        try {
            // Récupérer la liste des événements à partir de la méthode afficherEvents()
            List<Event> events = rec.afficherEvents();
            System.out.println("Reclamations loaded: " + events);

            // Créer une ObservableList à partir de la liste d'événements
            ObservableList<Event> observableList = FXCollections.observableList(events);

            // Définir les éléments de la TableView avec la liste observable
            tableView.setItems(observableList);

            // Configuration des PropertyValueFactory pour chaque colonne de la TableView
            NomECol.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
            StartDateCol.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            EndDateCol.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

            // Actualiser la TableView pour refléter les changements
            tableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Échec de l'initialisation de la TableView : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void deleteEvent(ActionEvent actionEvent) {
        Event selectedEvent = tableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                rec.deleteEvent(selectedEvent.getId());
                // Appeler la méthode deleteEvent() pour supprimer l'événement sélectionné
                //deleteEvent(selectedEvent.getId());
                // Mettre à jour la TableView en supprimant l'événement sélectionné de la liste des éléments
                tableView.getItems().remove(selectedEvent);
                showAlert("Suppression réussie", "L'événement a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la suppression de l'événement : " + e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un événement à supprimer.");
        }
    }
    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterEvent.fxml")));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierEvent(ActionEvent event) {
        Event selectedEvent = tableView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
                Parent root = loader.load();

                ModifierEvent controller = loader.getController();
                controller.setSelectedEvent(selectedEvent);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun événement sélectionné.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sélection manquante");
            alert.setContentText("Veuillez sélectionner un événement à modifier.");
            alert.showAndWait();
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
