package com.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.rankup.entities.Subscription_plan;
import com.example.rankup.services.Subscription_PlanService;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
public class AddSubscriptionPlan {

    @FXML
    private TextField TypeTF;

    @FXML
    private TextField PrixTF;

    @FXML
    private TextField AInfoTF;

    private final Subscription_PlanService rec = new Subscription_PlanService();

    @FXML
    void AddPlan(ActionEvent actionEvent) {
        try {
            // Validate input
            if (TypeTF.getText().isEmpty() || PrixTF.getText().isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            float price = Float.parseFloat(PrixTF.getText());

            // Add subscription plan
            rec.add(new Subscription_plan(TypeTF.getText(), AInfoTF.getText(), price));

            // Clear fields after adding
            TypeTF.clear();
            PrixTF.clear();
            AInfoTF.clear();

            showAlert("Success", "Subscription plan added successfully.");
            showNotification("Success", "Reservation added successfully.");

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid price format.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void naviguer(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ShowSubscriptionPlan.fxml"));
            TypeTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
