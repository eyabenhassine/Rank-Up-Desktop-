package com.example.rankup.controllers;

import com.example.rankup.entities.Reclamation;
import com.example.rankup.entities.SuiviReclamation;
import com.example.rankup.services.ReclamationService;
import com.example.rankup.services.SuiviReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;



public class SuiviReclamationController {
    @FXML
    private TextField idField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField idRecField;
    @FXML
    private TextField statusField;
    @FXML
    private ComboBox<Reclamation> reclamationComboBox;
    @FXML
    private TableView<SuiviReclamation> tableView;
    @FXML
    private TableColumn<SuiviReclamation, Integer> idRecCol;
    @FXML
    private TableColumn<SuiviReclamation, String> statusCol;
    @FXML
    private TableColumn<SuiviReclamation, String> descriptionCol;
    @FXML
    private TableColumn<SuiviReclamation, String> dateCol;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> colonneChoiceBox;
    @FXML
    private ChoiceBox<String> ordreChoiceBox;

    private SuiviReclamationService suiviRecService = new SuiviReclamationService();
    ReclamationService reclamationService = new ReclamationService();
    SuiviReclamationService suiviReclamationService = new SuiviReclamationService();

    @FXML
    public void initialize() {
        idRecCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
// Ajouter un écouteur de changement au champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchSuiviReclamation(newValue.trim()); // Appeler la méthode search avec le nouveau texte
        });
        // Remplir le TableView avec les données
        populateTableView();
        // Remplir la ComboBox avec les réclamations
        populateReclamations();
    }

    private void populateTableView() {
        try {
            // Récupérer les données à afficher
            List<SuiviReclamation> suiviReclamationList = suiviReclamationService.show();
            // Ajouter les données au TableView
            tableView.getItems().addAll(suiviReclamationList);
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Gérer l'exception (affichage d'un message d'erreur, journalisation, etc.)
        }
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

    public void ajouterSuiviReclamation(ActionEvent actionEvent) {
        // Récupérer la réclamation sélectionnée dans la ComboBox
        Reclamation selectedReclamation = reclamationComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String status = statusField.getText();
        String description = descriptionField.getText();

        // Vérifier si toutes les valeurs sont renseignées
        if (selectedReclamation != null && date != null && !status.isEmpty() && !description.isEmpty()) {
            try {
                // Créer un nouvel objet SuiviReclamation
                SuiviReclamation suiviReclamation = new SuiviReclamation();
                suiviReclamation.setReclamation(selectedReclamation);
                suiviReclamation.setDate(Date.valueOf(date));
                suiviReclamation.setStatus(status);
                suiviReclamation.setDescription(description);

                // Appeler la méthode add de SuiviReclamationService
                suiviReclamationService.add(suiviReclamation);

                // Ajouter la nouvelle SuiviReclamation à la TableView
                tableView.getItems().add(suiviReclamation);

                // Afficher un message de succès ou effectuer toute autre action nécessaire
                System.out.println("Suivi de réclamation ajouté avec succès !");
                showNotification("Ajout réussie", " Suivi_Reclamation ajoutée avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception (affichage d'un message d'erreur, journalisation, etc.)
            }
        } else {
            // Gérer le cas où toutes les valeurs ne sont pas renseignées
            System.err.println("Veuillez remplir tous les champs.");
        }
    }


    public void deleteSuivi(ActionEvent actionEvent) {
        SuiviReclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                suiviRecService.delete(selectedReclamation.getId());
                tableView.getItems().remove(selectedReclamation);  // Update the table view
                showAlert("Deletion Successful", "The reclamation has been deleted successfully.");
                showNotification("suppression réussie", " Suivi_Reclamation supprimée avec succès.");

            } catch (SQLException e) {
                showAlert("Error", "Failed to delete the reclamation: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a reclamation to delete.");
        }
    }

    public void modifiersuivi(ActionEvent actionEvent) {

        SuiviReclamation suiviReclamationSelectionee = tableView.getSelectionModel().getSelectedItem();
        if (suiviReclamationSelectionee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateSuiviReclamation.fxml"));
                Parent root = loader.load();
                System.out.println("FXML file loaded successfully.");
                UpdateSuiviReclamationController controller = loader.getController();
                System.out.println("Controller initialized.");

                controller.setSuivi(suiviReclamationSelectionee, suiviReclamationSelectionee.getId());
                System.out.println("Data initialized in controller.");

                Stage stage = new Stage();
                stage.setTitle("Liste des Factures");
                stage.setScene(new Scene(root));

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun appartement sélectionné.");
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
    private void handleSuiviReclamationSearch(KeyEvent event) {
        String searchText = searchField.getText().trim();
        searchSuiviReclamation(searchText);
    }

    private void searchSuiviReclamation(String searchText) {
        final String finalSearchText = searchText.toLowerCase().trim();

        if (!finalSearchText.isEmpty()) {
            List<SuiviReclamation> searchResults = suiviRecService.chercher(finalSearchText);
            ObservableList<SuiviReclamation> observableList = FXCollections.observableArrayList(searchResults);

            FilteredList<SuiviReclamation> filteredList = new FilteredList<>(observableList, p -> true);

            filteredList.setPredicate(suiviReclamation -> {
                String lowerCaseNom = suiviReclamation.getStatus().toLowerCase();
                String lowerCaseType = suiviReclamation.getDescription().toLowerCase();
                String lowerCaseDescription = suiviReclamation.getDescription().toLowerCase();
                String lowerCaseDate = suiviReclamation.getDate().toString().toLowerCase();

                return lowerCaseNom.contains(finalSearchText)
                        || lowerCaseType.contains(finalSearchText)
                        || lowerCaseDescription.contains(finalSearchText)
                        || lowerCaseDate.contains(finalSearchText);
            });

            tableView.setItems(filteredList);
        } else {
            try {
                loadSuiviReclamationData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void loadSuiviReclamationData() throws SQLException {
        List<SuiviReclamation> suiviReclamationList = suiviRecService.show();
        ObservableList<SuiviReclamation> observableList = FXCollections.observableList(suiviReclamationList);
        tableView.setItems(observableList);
    }

    public void trier(ActionEvent actionEvent) {
        Comparator<SuiviReclamation> comparator = getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox);
        if (comparator != null) {
            tableView.getItems().sort(comparator);
        }
    }
    private Comparator<SuiviReclamation> getComparatorFromChoiceBox(ChoiceBox<String> colonneChoiceBox, ChoiceBox<String> ordreChoiceBox) {
        String selectedColumn = colonneChoiceBox.getValue();
        String selectedOrder = ordreChoiceBox.getValue();

        Comparator<SuiviReclamation> comparator = null;

        switch (selectedColumn) {

            case "status":
                comparator = Comparator.comparing(SuiviReclamation::getStatus);
                break;
            case "description":
                comparator = Comparator.comparing(SuiviReclamation::getDescription);
                break;
            case "date":
                comparator = Comparator.comparing(SuiviReclamation::getDate);
                break;
            default:
                break;
        }

        if (comparator != null && selectedOrder != null && selectedOrder.equals("DESC")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    public void naviguerToSuivi(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/suiviReclamationView.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void logout(MouseEvent mouseEvent) {
    }

    public void naviguer(ActionEvent actionEvent) {
        rafraichirChamps(); // Appeler la méthode pour rafraîchir les champs
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/suiviReclamationView.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void rafraichirChamps() {
        idField.clear(); // Effacer le contenu de idField
        datePicker.setValue(null); // Remettre la valeur de datePicker à null
        statusField.clear(); // Effacer le contenu de statusField
        descriptionField.clear(); // Effacer le contenu de descriptionField
        reclamationComboBox.getSelectionModel().clearSelection(); // Désélectionner tout dans la combobox
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























