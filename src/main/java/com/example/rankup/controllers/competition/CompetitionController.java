package com.example.rankup.controllers.competition;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    }

}
