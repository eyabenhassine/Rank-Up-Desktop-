package com.example.rankup.controllers;

import com.example.rankup.entities.MapEntity;
import com.example.rankup.services.MapService;
import com.example.rankup.services.SessionManager;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class AdminCompetitionController {
    @FXML
    private AnchorPane stageAnchor;

    @FXML
    void navToEquipe(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminequipe.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Competition");
        stage.show(); // Use show() instead of showAndWait()

        // Close the current stage immediately
        Stage currentStage = (Stage) stageAnchor.getScene().getWindow();
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    void navToMatch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/match1.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Competition");
            stage.show(); // Use show() instead of showAndWait()

            // Close the current stage immediately
            Stage currentStage = (Stage) stageAnchor.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openMap(ActionEvent event) {
        Stage stage = new Stage();
//        popupStage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("JMapViewer in JavaFX");
        stage.setWidth(800);
        stage.setHeight(600);

        JMapViewer mapViewer = new JMapViewer();
        Coordinate france = new Coordinate(46.603354, 1.888334);
        mapViewer.setDisplayPosition(france, 6);

        // Coordinates for France
        Coordinate markerFrancePosition = new Coordinate(46.603354, 1.888334);
        MapMarkerDot markerFrance = new MapMarkerDot("France", markerFrancePosition);
        mapViewer.addMapMarker(markerFrance);

        // Add mouse listener to the map viewer
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the mouse click position
                double lat = mapViewer.getPosition(e.getPoint()).getLat();
                double lon = mapViewer.getPosition(e.getPoint()).getLon();

                MapService mapService = new MapService();
                mapService.ajouter(new MapEntity(lat, lon));

                // Show an alert dialog with latitude and longitude on the JavaFX Application Thread
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Marker Clicked");
                    alert.setHeaderText(null);
                    alert.setContentText("Location Set to: Latitude: " + lat + "\nLongitude: " + lon);
                    alert.showAndWait();
                });
            }
        });

        SwingNode swingNode = new SwingNode();
        swingNode.setContent(mapViewer);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(swingNode);

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }
}
