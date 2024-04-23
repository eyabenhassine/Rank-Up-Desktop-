package com.example.rankup.controllers;

import com.example.rankup.entities.User;
import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangepasswordController {

    @FXML
    private PasswordField oldpass;
    @FXML
    private PasswordField newpass;
    @FXML
    private PasswordField confnewpass;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
    }

    @FXML
    private void logout() {
        SessionManager.clearSession();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");

            Stage currentStage = (Stage) oldpass.getScene().getWindow();
            currentStage.close();

            // Show the login stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveaction() {
        String oldPassword = oldpass.getText();
        String newPassword = newpass.getText();
        String confirmNewPassword = confnewpass.getText();

        // Check if new password and confirm new password match
        if (!newPassword.equals(confirmNewPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("New password and confirm new password do not match.");
            alert.showAndWait();
            return;
        }

        // Retrieve user ID from session
        int userId = (int) SessionManager.getSession("userId");

        // Check if old password is correct
        String loginResult = userService.loginUser(SessionManager.getSession("email").toString(), oldPassword);
        if (loginResult == null) {
            // Update password
            if (userService.changePassword(userId, newPassword)) {
                // Password changed successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Password changed successfully.");
                alert.showAndWait();
            } else {
                // Failed to change password
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to change password. Please try again later.");
                alert.showAndWait();
            }
        } else {
            // Old password is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Old password is incorrect.");
            alert.showAndWait();
        }
    }


    @FXML
    private void home() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Profile");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage currentStage = (Stage) oldpass.getScene().getWindow();
        currentStage.close();
    }
}
