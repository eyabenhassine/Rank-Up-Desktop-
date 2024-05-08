package com.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import com.example.rankup.entities.Reservation;
import com.example.rankup.services.ReservationService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import javafx.scene.control.DateCell;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

import java.io.IOException;
import java.sql.Date;

public class AddReservation {

    @FXML
    private DatePicker DateDP;

    @FXML
    private TextField DescriptionTF;

    private final ReservationService reservationService = new ReservationService();

    public void initialize() {
        // Set the day cell factory to disable past and current dates
        DateDP.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Disable past and current dates
                if (item.isBefore(LocalDate.now()) || item.isEqual(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Change color for disabled dates
                }
            }
        });
    }




    @FXML
    void addReservation(ActionEvent event) {
        try {
            // Validate input
            if (DateDP.getValue() == null || DescriptionTF.getText().isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            // Convert DatePicker value to SQL Date
            Date date = Date.valueOf(DateDP.getValue());

            // Add reservation
            reservationService.add(new Reservation(date, DescriptionTF.getText()));

            // Clear fields after adding
            DateDP.setValue(null);
            DescriptionTF.clear();

            showAlert("Success", "Reservation added successfully.");
            showNotification("Success", "Reservation added successfully.");



            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            showAlert("Error", "An error occurred while adding the reservation.");
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
            showAlert("Error", "An error occurred while loading the new interface.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        trayIcon.displayMessage(title, message, MessageType.INFO);
    }




}
