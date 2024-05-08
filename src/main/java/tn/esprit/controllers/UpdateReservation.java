package tn.esprit.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Reservation;
import tn.esprit.services.ReservationService;

import java.awt.*;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

public class UpdateReservation {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionTextArea;

    private ReservationService reservationService;
    private Reservation reservationToUpdate;

    public void initialize() {
        reservationService = new ReservationService();
    }

    public void setReservationToUpdate(Reservation reservation) {
        this.reservationToUpdate = reservation;
        // Populate fields with current reservation data
        if (reservation != null) {
            datePicker.setValue(reservation.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            descriptionTextArea.setText(reservation.getDescription());
        }
    }

    @FXML
    void updateReservation() {
        if (reservationToUpdate != null) {
            Date newDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String newDescription = descriptionTextArea.getText();

            // Update reservation
            reservationToUpdate.setDate(newDate);
            reservationToUpdate.setDescription(newDescription);
            reservationService.update(reservationToUpdate);

            showAlert("Success", "Reservation updated successfully.");
            showNotification("Success", "Reservation updated successfully.");

        } else {
            showErrorAlert("Error", "No reservation selected.");
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

    private void showNotification(String title, String message) {
        // Check if SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        // Get the SystemTray instance
        SystemTray tray = SystemTray.getSystemTray();

        // Load an image for the tray icon
        Image image = Toolkit.getDefaultToolkit().createImage("path/to/icon.png");

        // Create a TrayIcon
        TrayIcon trayIcon = new TrayIcon(image, "Reservation Notification");

        // Set auto-size property to fit the image size
        trayIcon.setImageAutoSize(true);

        try {
            // Add the TrayIcon to the SystemTray
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        // Display the notification
        trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
    }
}
