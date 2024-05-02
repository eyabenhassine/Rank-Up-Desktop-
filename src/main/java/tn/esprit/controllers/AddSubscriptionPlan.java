package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tn.esprit.entities.Subscription_plan;
import tn.esprit.services.Subscription_PlanService;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid price format.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
