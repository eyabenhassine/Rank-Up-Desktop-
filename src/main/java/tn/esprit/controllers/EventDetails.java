package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.entities.Event;

public class EventDetails {
    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventStartDateLabel;

    @FXML
    private Label eventEndDateLabel;

    @FXML
    private Label eventTypeLabel;

    @FXML
    private Label eventDescriptionLabel;

    public void initialize(Event event) {
        eventNameLabel.setText(event.getNom_event());
        eventStartDateLabel.setText("Start Date: " + event.getDate_debut());
        eventEndDateLabel.setText("End Date: " + event.getDate_fin());
        eventTypeLabel.setText("Type: " + event.getType());
        eventDescriptionLabel.setText("Description: " + event.getDescription());
    }
}
