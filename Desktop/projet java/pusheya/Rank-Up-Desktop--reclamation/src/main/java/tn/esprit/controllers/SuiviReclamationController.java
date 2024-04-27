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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Suivi_Reclamation;
import tn.esprit.services.Suivi_ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

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
    private TableView<Suivi_Reclamation> tableView;
    @FXML
    private TableColumn<Suivi_Reclamation, Integer> idRecCol;
    @FXML
    private TableColumn<Suivi_Reclamation, String> statusCol;
    @FXML
    private TableColumn<Suivi_Reclamation, String> descriptionCol;
    @FXML
    private TableColumn<Suivi_Reclamation, String> dateCol;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> colonneChoiceBox;
    @FXML
    private ChoiceBox<String> ordreChoiceBox;

    private Suivi_ReclamationService suiviRecService = new Suivi_ReclamationService();

    @FXML
    public void initialize() {
        //  System.out.println("idField: " + idField);
        idRecCol.setCellValueFactory(new PropertyValueFactory<>("idRec"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        loadSuiviReclamationData();

    }

    private void loadSuiviReclamationData() {
        List<Suivi_Reclamation> suiviReclamationList = suiviRecService.getAllSuiviReclamations();
        ObservableList<Suivi_Reclamation> observableList = FXCollections.observableList(suiviReclamationList);
        tableView.setItems(observableList);
    }


    @FXML
    void modifiersuivi(ActionEvent event) {
        Suivi_Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateSuiviReclamation.fxml"));  // Corrected FXML file name
                Parent root = loader.load();

                UpdateSuiviReclamationController controller = loader.getController();
                controller.setSuiviReclamation(selectedReclamation);  // Correct method to pass the selected reclamation

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Missing");
            alert.setContentText("Please select a reclamation to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteSuivi(ActionEvent event) {
        Suivi_Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                suiviRecService.deleteSuiviReclamation(selectedReclamation.getId());
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


    public void ajouterSuiviReclamation(ActionEvent actionEvent) {
        try {
            String idRecText = idRecField.getText().trim();
            if (idRecText.isEmpty()) {
                showAlert("Input Error", "Please enter a Reclamation ID");
                return;
            }
            int idRec = Integer.parseInt(idRecText);

            // Créer un nouvel objet Suivi_Reclamation en utilisant le deuxième constructeur
            Suivi_Reclamation suiviReclamation = new Suivi_Reclamation(idRec, statusField.getText(), descriptionField.getText(), datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));

            // Ajouter le nouvel objet à la liste et rafraîchir la TableView
            suiviRecService.add(suiviReclamation);
            loadSuiviReclamationData();

            // Afficher un message de réussite
            showAlert("Success", "Suivi Reclamation added successfully");

            // Effacer les champs de saisie
            idRecField.clear();
            statusField.clear();
            descriptionField.clear();
            datePicker.setValue(null);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for Reclamation ID");
        } catch (SQLException e) {
            showAlert("Database Error", "Error adding Suivi Reclamation: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    public void search(ActionEvent actionEvent) {
        String searchText = searchField.getText().trim();

        if (!searchText.isEmpty()) {
            List<Suivi_Reclamation> searchResults = suiviRecService.chercher(searchText);
            ObservableList<Suivi_Reclamation> observableList = FXCollections.observableList(searchResults);
            tableView.setItems(observableList);
        } else {
            // If search text is empty, load all Suivi_Reclamations into the TableView
            loadSuiviReclamationData();
        }

    }



    private Comparator<Suivi_Reclamation> getComparatorFromChoiceBox(ChoiceBox<String> colonneChoiceBox, ChoiceBox<String> ordreChoiceBox) {
        String selectedColumn = colonneChoiceBox.getValue();
        String selectedOrder = ordreChoiceBox.getValue();

        Comparator<Suivi_Reclamation> comparator = null;

        switch (selectedColumn) {
            case "idRec":
                comparator = Comparator.comparing(Suivi_Reclamation::getIdRec);
                break;
            case "status":
                comparator = Comparator.comparing(Suivi_Reclamation::getStatus);
                break;
            case "description":
                comparator = Comparator.comparing(Suivi_Reclamation::getDescription);
                break;
            case "date":
                comparator = Comparator.comparing(Suivi_Reclamation::getDate);
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
        Comparator<Suivi_Reclamation> comparator = getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox);
        if (comparator != null) {
            tableView.getItems().sort(comparator);
        }
    }
    @FXML
    void naviguerToSuivi(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/suiviReclamationView.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void logout(MouseEvent mouseEvent) {
    }
}
