package com.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class admin {

    @FXML
    void naviguertoEvents(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherEvent.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void naviguerToSuivuList(ActionEvent actionEvent) {
    }
}
