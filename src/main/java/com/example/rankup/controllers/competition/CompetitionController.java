package com.example.rankup.controllers.competition;

import com.example.rankup.entities.MapEntity;
import com.example.rankup.services.MapService;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import java.io.IOException;

public class CompetitionController {

    @FXML
    private AnchorPane stageAnchor;

    @FXML
    void navToEquipe(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipe.fxml"));
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

        MapService mapService = new MapService();
        MapEntity mapEntity = mapService.getLastElement();

        JMapViewer mapViewer = new JMapViewer();
        Coordinate match = new Coordinate(mapEntity.getLatitude(), mapEntity.getLongitude());
        mapViewer.setDisplayPosition(match, 6);

        // Coordinates for match

        MapMarkerDot markerMatch = new MapMarkerDot("Current Match", match);

        mapViewer.addMapMarker(markerMatch);



        SwingNode swingNode = new SwingNode();
        swingNode.setContent(mapViewer);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(swingNode);

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

}
