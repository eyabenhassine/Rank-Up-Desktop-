package com.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.rankup.entities.Event;
import com.example.rankup.services.EventService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherEClient {
    @FXML
    private GridPane eventGrid;

    private final EventService eventService = new EventService();

    public AfficherEClient() throws IOException, SQLException {
    }

    // Méthode pour charger les données d'événement dans les labels appropriés
    public void initialize() {
        // Supposons que vous avez une liste d'événements
        List<Event> events = eventService.getAll(); // Obtenir vos événements depuis quelque part

        // Boucle à travers la liste et afficher chaque événement dans une ligne de la grille
        int rowIndex = 1; // Commencez à partir de la deuxième ligne après le libellé de l'en-tête
        for (Event event : events) {
            Label eventNameLabel = new Label(event.getNom_event());
            Label eventStartDateLabel = new Label(event.getDate_debut());
            Label eventEndDateLabel = new Label(event.getDate_fin());
            Label eventTypeLabel = new Label(event.getType());
            Button detailsButton = new Button("Détails");
            detailsButton.setOnAction(e -> showEventDetails(event)); // Passer l'événement à la méthode showEventDetails

            eventGrid.addRow(rowIndex++, eventNameLabel, eventStartDateLabel, eventEndDateLabel, eventTypeLabel, detailsButton);
        }
    }

    // Méthode pour afficher les détails d'un événement
    private void showEventDetails(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetails.fxml"));
            Parent root = loader.load();
            EventDetails controller = loader.getController();
            controller.initialize(event);
            Stage currentStage = (Stage) eventGrid.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEventDetails(ActionEvent event) {
    }

    public void BackToHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
