package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.ChoiceBox;


import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


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

    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> colonneChoiceBox;

    @FXML
    private ChoiceBox<String> ordreChoiceBox;



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


    public void naviguerAdd(ActionEvent actionEvent) {
        // Implémentez la navigation vers l'ajout si nécessaire
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterEvent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherAlerteErreur("Erreur", "Impossible de charger la vue d'ajout d'événement.");
        }
    }
    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void search(ActionEvent actionEvent) {
        String searchText = searchField.getText().trim();

        if (!searchText.isEmpty()) {
            List<Event> searchResults = null;
            try {
                searchResults = rec.chercher(searchText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Convert the list to observable list
            ObservableList<Event> observableList = FXCollections.observableArrayList(searchResults);

            // Define the cell value factories for each column
            NomECol.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
            StartDateCol.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            EndDateCol.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));



            // Set the items to the table
            tableView.setItems(observableList);

            // Create a sorted list to sort the table based on the selected column and order
            SortedList<Event> sortedList = new SortedList<>(observableList);

            // Set the comparator based on the selected column and order
            Comparator<Event> comparator = getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox);
            sortedList.setComparator(comparator);

            // Bind the sorted list to the table
            tableView.setItems(sortedList);
        } else {
            showAlert("Input Error", "Please enter a search term.");
        }

    }

    private Comparator<Event> getComparatorFromChoiceBox(ChoiceBox<String> colonneChoiceBox, ChoiceBox<String> ordreChoiceBox) {
        String selectedColumn = colonneChoiceBox.getValue();
        String selectedOrder = ordreChoiceBox.getValue();

        Comparator<Event> comparator = null;

        switch (selectedColumn) {
            case "Nom":
                comparator = Comparator.comparing(Event::getNom_event);
                break;
            case "Starting Day":
                comparator = Comparator.comparing(Event::getDate_debut);
                break;
            case "End Day":
                comparator = Comparator.comparing(Event::getDate_fin);
                break;
            case "Type":
                comparator = Comparator.comparing(Event::getType);
                break;
            case "Description":
                comparator = Comparator.comparing(Event::getDescription);
                break;

            default:
                break;
        }

        if (comparator != null && selectedOrder != null && selectedOrder.equals("DESC")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    @FXML
    void trier(ActionEvent event) {
        tableView.getItems().sort(getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox));
    }
}
