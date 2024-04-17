package com.example.rankup.controllers;

import com.example.rankup.services.UserService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class logincontroller {

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    private Stage stage;
    @FXML
    private ProgressIndicator loadingindicator;

    @FXML
    void handleLoginButton(ActionEvent event) {
        String email = emailLogin.getText();
        String password = passwordLogin.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both email and password.");
            alert.show();
            return;
        }

        loadingindicator.setVisible(true);

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() {
                UserService userService = new UserService();
                return userService.loginUser(email, password);
            }
        };

        loginTask.setOnSucceeded(e -> {
            boolean loginSuccess = loginTask.getValue();
            loadingindicator.setVisible(false);

            if (loginSuccess) {

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
                    emailLogin.getScene().setRoot(root);
                } catch (IOException ex) {
                    ex.printStackTrace();

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid email or password. Please try again.");
                alert.show();
            }
        });

        loginTask.setOnFailed(e -> {
            loadingindicator.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred while logging in. Please try again later.");
            alert.show();
        });

        new Thread(loginTask).start();



    }


    @FXML
    private void signupaction() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
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

        Stage currentStage = (Stage) emailLogin.getScene().getWindow();
        currentStage.close();
    }
    // Close the current dialog


}
