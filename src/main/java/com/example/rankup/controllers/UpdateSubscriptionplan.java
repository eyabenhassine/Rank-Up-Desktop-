package com.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.rankup.entities.Subscription_plan;
import com.example.rankup.services.Subscription_PlanService;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class UpdateSubscriptionplan {

    @FXML
    private TextField TypeTF;

    @FXML
    private TextField PrixTF;

    @FXML
    private TextField AInfoTF;

    private Subscription_PlanService subscriptionPlanService;
    private Subscription_plan selectedPlan;

    public void initData(Subscription_plan plan) {
        if (plan != null) {
            selectedPlan = plan;
            TypeTF.setText(selectedPlan.getType());
            PrixTF.setText(String.valueOf(selectedPlan.getPrix()));
            AInfoTF.setText(selectedPlan.getAdditional_info());
        } else {
            showErrorAlert("Error", "No subscription plan selected.");
            navigateBack();
        }
        try {
            subscriptionPlanService = new Subscription_PlanService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updatePlan(ActionEvent event) {
        if (selectedPlan != null) {
            try {
                String type = TypeTF.getText();
                String prixText = PrixTF.getText();
                String additionalInfo = AInfoTF.getText();

                if (type.isEmpty() || prixText.isEmpty()) {
                    showErrorAlert("Error", "Please fill in all fields.");
                    return;
                }

                float prix = Float.parseFloat(prixText);

                selectedPlan.setType(type);
                selectedPlan.setPrix(prix);
                selectedPlan.setAdditional_info(additionalInfo);

                subscriptionPlanService.update(selectedPlan);

                showAlert("Success", "Subscription plan updated successfully.");
                showNotification("Success", "Subscription plan updated successfully.");
            } catch (NumberFormatException e) {
                showErrorAlert("Error", "Invalid price format.");
            } catch (Exception e) {
                showErrorAlert("Error", "Failed to update subscription plan: " + e.getMessage());
            }
        } else {
            showErrorAlert("Error", "No subscription plan selected.");
            navigateBack();
        }
    }

    @FXML
    void handleImageClick(ActionEvent event) {
        navigateBack();
    }

    private void navigateBack() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home.fxml")));
            Stage stage = (Stage) TypeTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error", "An error occurred while loading the home interface.");
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
        TrayIcon trayIcon = new TrayIcon(image, "Subscription Plan Notification");

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
