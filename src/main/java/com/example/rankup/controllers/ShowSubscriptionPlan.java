package com.example.rankup.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import com.example.rankup.entities.Subscription_plan;
import com.example.rankup.services.Subscription_PlanService;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;


import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ShowSubscriptionPlan {

    @FXML
    private TableView<Subscription_plan> planTableView;

    @FXML
    private TableColumn<Subscription_plan, Integer> idColumn;

    @FXML
    private TableColumn<Subscription_plan, String> typeColumn;

    @FXML
    private TableColumn<Subscription_plan, Float> priceColumn;

    @FXML
    private Label selectedTypeLabel;
    @FXML
    private Label selectedAdditionalInfoLabel;
    @FXML
    private Label selectedPriceLabel;



    @FXML
    private TableColumn<Subscription_plan, String> additionalInfoColumn;

    private final Subscription_PlanService service = new Subscription_PlanService();

    @FXML
    public void initialize() {
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        additionalInfoColumn.setCellValueFactory(new PropertyValueFactory<>("additional_info"));

        // Load data into the table
        loadSubscriptionPlans();

        // Listen for selection changes and display details of the selected plan
        planTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displaySelectedSubscriptionPlanDetails(newSelection);
            } else {
                clearSelectedSubscriptionPlanDetails();
            }
        });
    }

     private void displaySelectedSubscriptionPlanDetails(Subscription_plan plan) {
        // Display the details of the selected subscription plan
        System.out.println("Selected Subscription Plan: " + plan);

        // You can display these details wherever you want
        // For example, display them in labels next to the table
        selectedTypeLabel.setText(plan.getType());
        selectedAdditionalInfoLabel.setText(plan.getAdditional_info());
        selectedPriceLabel.setText(String.valueOf(plan.getPrix()));
    }



    private void clearSelectedSubscriptionPlanDetails() {
        // Clear the displayed details
        selectedTypeLabel.setText("");
        selectedAdditionalInfoLabel.setText("");
        selectedPriceLabel.setText("");
    }


    private void loadSubscriptionPlans() {
        try {
            List<Subscription_plan> subscriptionPlans = service.getAll();
            ObservableList<Subscription_plan> planObservableList = FXCollections.observableArrayList(subscriptionPlans);
            planTableView.setItems(planObservableList);
        } catch (Exception e) {
            showErrorAlert("Error", "Failed to load subscription plans.");
        }
    }

    @FXML
    void updateSubscriptionPlan(ActionEvent event) {
        Subscription_plan selectedPlan = planTableView.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatesubscriptionplan1.fxml"));
                Parent root = loader.load();

                // Get the controller of the UpdateSubscriptionplan interface
                UpdateSubscriptionplan controller = loader.getController();

                // Pass the selected subscription plan data to the controller
                controller.initData(selectedPlan);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showErrorAlert("Error", "Please select a subscription plan to update.");
        }
    }

    @FXML
    void deleteSubscriptionPlan(ActionEvent actionEvent) {
        Subscription_plan selectedPlan = planTableView.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            try {
                Subscription_PlanService service = new Subscription_PlanService();

                // Call the delete method of Subscription_PlanService passing the ID of the selected plan
                service.delete(selectedPlan.getId());

                // If deletion is successful
                planTableView.getItems().remove(selectedPlan);
                showAlert("Success", "Subscription plan deleted successfully.");
                showNotification("Success", "Subscription plan deleted successfully.");
            } catch (Exception e) {
                // Handle any exceptions that may occur during deletion
                showErrorAlert("Error", "Failed to delete subscription plan: " + e.getMessage());
            }
        } else {
            // If no plan is selected
            showErrorAlert("Error", "Please select a subscription plan to delete.");
        }
    }

    @FXML
    void navigateToDifferentInterface(ActionEvent event) {
        try {
            // Load the new interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddSubscriptionPlan.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error loading the new interface
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


    /*public void displaySelectedSubscriptionPlanDetails(ActionEvent actionEvent) {
        Subscription_plan selectedPlan = planTableView.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SubPlanDetails.fxml"));
                Parent root = loader.load();

                // Get the controller of the SubscriptionPlanDetails interface
                SubPlanDetails controller = loader.getController();

                // Pass the subscription plan details to the controller
                controller.setSubscriptionPlanDetails(selectedPlan.getType(), selectedPlan.getAdditional_info(), String.valueOf(selectedPlan.getPrix()));

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If no plan is selected, clear the displayed details
            clearSelectedSubscriptionPlanDetails();
        }
    }*/
}

