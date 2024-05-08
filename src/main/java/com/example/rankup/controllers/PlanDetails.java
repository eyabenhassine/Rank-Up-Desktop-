package com.example.rankup.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.rankup.entities.Subscription_plan;
import javafx.event.ActionEvent;

public class PlanDetails {

    @FXML
    private Label idLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label additionalInfoLabel;

    public void initialize(Subscription_plan plan) {
        idLabel.setText(Integer.toString(plan.getId()));
        typeLabel.setText(plan.getType());
        priceLabel.setText(Float.toString(plan.getPrix()));
        additionalInfoLabel.setText(plan.getAdditional_info());
    }

    @FXML
    private void handleParticipateButtonAction(ActionEvent event) {
        // Implement your participation logic here
        System.out.println("Participate button clicked for plan id: " );
    }
}
