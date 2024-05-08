package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Reservation;
import tn.esprit.services.ReservationService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.util.List;

public class ShowReservation {

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> idColumn;

    @FXML
    private TableColumn<Reservation, String> dateColumn;

    @FXML
    private TableColumn<Reservation, String> descriptionColumn;

    private ReservationService reservationService = new ReservationService();

    @FXML
    void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // Load reservations
        refresh();
    }

    @FXML
    void refresh() {
        try {
            // Get reservations from the service
            List<Reservation> reservations = reservationService.getAll();

            // Set the items to the table
            reservationTable.getItems().setAll(reservations);
        } catch (Exception e) {
            showErrorAlert("Error", "An error occurred while loading reservations.");
        }
    }

    @FXML
    void deleteReservation(ActionEvent actionEvent) {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                // Call the delete method of ReservationService passing the ID of the selected reservation
                reservationService.delete(selectedReservation.getId());

                // If deletion is successful
                reservationTable.getItems().remove(selectedReservation);
                showAlert("Success", "Reservation deleted successfully.");
            } catch (Exception e) {
                // Handle any exceptions that may occur during deletion
                showErrorAlert("Error", "Failed to delete reservation: " + e.getMessage());
            }
        } else {
            // If no reservation is selected
            showErrorAlert("Error", "Please select a reservation to delete.");
        }
    }

    @FXML
    void handleImageClick(MouseEvent event) {
        try {
            // Load the new interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error", "An error occurred while loading the new interface.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
