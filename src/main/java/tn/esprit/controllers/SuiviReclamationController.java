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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Suivi_Reclamation;
import tn.esprit.services.Suivi_ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SuiviReclamationController {

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

    private Suivi_ReclamationService suiviRecService = new Suivi_ReclamationService();

    @FXML
    public void initialize() {
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
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterSuiviReclamation.fxml"));
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

}
