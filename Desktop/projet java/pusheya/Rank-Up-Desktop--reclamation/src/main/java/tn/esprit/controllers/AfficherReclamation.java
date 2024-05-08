package tn.esprit.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Suivi_Reclamation;
import tn.esprit.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AfficherReclamation {

    @FXML
    private TableColumn<Reclamation, Integer> NumtelCol;

    @FXML
    private TableColumn<Reclamation, String> dateCol;

    @FXML
    private TableColumn<Reclamation, String> descriptionCol;

    @FXML
    private TableColumn<Reclamation, String> nomcol;

    @FXML
    private TableView<Reclamation> tableView;

    @FXML
    private TableColumn<Reclamation, String> typeCol;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> colonneChoiceBox;

    @FXML
    private ChoiceBox<String> ordreChoiceBox;
    @FXML
    private Label selectedNomLabel;

    @FXML
    private Label selectedNumTelLabel;

    @FXML
    private Label selectedTypeLabel;

    @FXML
    private Label selectedDescriptionLabel;

    @FXML
    private Label selectedDateLabel;

    private final ReclamationService rec = new ReclamationService();

    @FXML
    void initialize() {
        try {
            List<Reclamation> reclamations = rec.afficherRec();
            System.out.println("Reclamations loaded: " + reclamations);

            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            tableView.setItems(observableList);

            nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            NumtelCol.setCellValueFactory(new PropertyValueFactory<>("NumTel"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

            tableView.refresh();

            // Ajouter un gestionnaire d'événements pour la sélection de la table
            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Afficher les détails de la réclamation sélectionnée
                    displaySelectedReclamationDetails(newSelection);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to initialize the TableView: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void displaySelectedReclamationDetails(Reclamation reclamation) {
        // Afficher les détails de la réclamation sélectionnée
        System.out.println("Selected Reclamation: " + reclamation);

        // Vous pouvez afficher ces détails où vous le souhaitez
        // Par exemple, affichez-les dans des labels à côté de la table
        selectedNomLabel.setText(reclamation.getNom());
        selectedNumTelLabel.setText(String.valueOf(reclamation.getNumTel()));
        selectedTypeLabel.setText(reclamation.getType());
        selectedDescriptionLabel.setText(reclamation.getDescription());
        selectedDateLabel.setText(reclamation.getDate());
    }









    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterReclamation.fxml")));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierReclamation(ActionEvent event) {
        Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateReclamation.fxml"));
                Parent root = loader.load();

                UpdateReclamation controller = loader.getController();
                controller.setSelectedReclamation(selectedReclamation);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No reclamation selected.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Missing");
            alert.setContentText("Please select a reclamation to modify.");
            alert.showAndWait();
        }
    }

    public void deleteReclamation(ActionEvent actionEvent) {
        Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                rec.deleteReclamation(selectedReclamation.getId());
                tableView.getItems().remove(selectedReclamation);  // Update the table view
                showAlert("Deletion Successful", "The reclamation has been deleted successfully.");
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete the reclamation: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a reclamation to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void search(ActionEvent actionEvent) {
        String searchText = searchField.getText().trim();

        if (!searchText.isEmpty()) {
            List<Reclamation> searchResults = null;
            try {
                searchResults = rec.chercher(searchText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Convert the list to observable list
            ObservableList<Reclamation> observableList = FXCollections.observableArrayList(searchResults);

            // Define the cell value factories for each column
            nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            NumtelCol.setCellValueFactory(new PropertyValueFactory<>("NumTel"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

            // Set the items to the table
            tableView.setItems(observableList);

            // Create a sorted list to sort the table based on the selected column and order
            SortedList<Reclamation> sortedList = new SortedList<>(observableList);

            // Set the comparator based on the selected column and order
            Comparator<Reclamation> comparator = getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox);
            sortedList.setComparator(comparator);

            // Bind the sorted list to the table
            tableView.setItems(sortedList);
        } else {
            showAlert("Input Error", "Please enter a search term.");
        }
    }

    @FXML
    void trier(ActionEvent event) {
        tableView.getItems().sort(getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox));
    }

    private Comparator<Reclamation> getComparatorFromChoiceBox(ChoiceBox<String> colonneChoiceBox, ChoiceBox<String> ordreChoiceBox) {
        String selectedColumn = colonneChoiceBox.getValue();
        String selectedOrder = ordreChoiceBox.getValue();

        Comparator<Reclamation> comparator = null;

        switch (selectedColumn) {
            case "Nom":
                comparator = Comparator.comparing(Reclamation::getNom);
                break;
            case "NumTel":
                comparator = Comparator.comparingInt(Reclamation::getNumTel);
                break;
            case "Type":
                comparator = Comparator.comparing(Reclamation::getType);
                break;
            case "Description":
                comparator = Comparator.comparing(Reclamation::getDescription);
                break;
            case "Date":
                comparator = Comparator.comparing(Reclamation::getDate);
                break;
            default:
                break;
        }

        if (comparator != null && selectedOrder != null && selectedOrder.equals("DESC")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }



    private void loadReclamationData() {
        List<Reclamation> reclamations = rec.afficherRec();
        ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
        tableView.setItems(observableList);
    }
    @FXML
    void trierReclamations(ActionEvent event) {
        String colonne = colonneChoiceBox.getValue();
        String ordre = ordreChoiceBox.getValue();

        try {
            List<Reclamation> reclamationsTrie = rec.trier(colonne, ordre);
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamationsTrie);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            // Gérer les exceptions...
        }
    }

}