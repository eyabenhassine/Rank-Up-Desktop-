package com.example.rankup.controllers;

import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserblockController {


@FXML
private TextArea blockreason;

@FXML
private ComboBox<String> blockedCB ;

    private int userId;
    private UserService userService;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    void initialize() {
        userService = new UserService();
        ObservableList<String> statutOptions = FXCollections.observableArrayList("active", "blocked");
        blockedCB.setItems(statutOptions);
        System.out.println("User ID: " + userId);
    }

    @FXML
    private void confirmblock() {
        String blockStatus = blockedCB.getValue();
        String blockReason = blockreason.getText();

        // Check if both block status and reason are selected
        if (blockStatus != null && blockReason != null) {
            // Call the service method to update user status and block reason
            boolean success = userService.updateUserStatusAndBlockReason(userId, blockStatus, blockReason);
            if (success) {
                System.out.println("User successfully blocked");
                closeWindow();
            } else {
                System.out.println("Failed to block user");
            }
        } else {
            System.out.println("Please select both block status and reason");
        }
    }
    private void closeWindow() {
        // Close the current window
        blockreason.getScene().getWindow().hide();
    }
    @FXML
    private void logout() {

        SessionManager.clearSession();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");

            Stage currentStage = (Stage) blockreason.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
