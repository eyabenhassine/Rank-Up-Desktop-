package com.example.rankup.controllers;

import com.example.rankup.services.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void navToBadges(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/badge.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Badges");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
