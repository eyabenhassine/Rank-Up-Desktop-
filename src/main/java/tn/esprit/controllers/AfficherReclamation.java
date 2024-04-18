package tn.esprit.controllers;

import javafx.beans.Observable;
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
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Suivi_Reclamation;
import tn.esprit.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
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
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to initialize the TableView: " + e.getMessage());
            alert.showAndWait();
        }
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
}
