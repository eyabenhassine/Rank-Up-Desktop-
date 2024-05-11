package com.example.rankup.controllers;

import com.example.rankup.services.SessionManager;
import com.example.rankup.services.SmsSender;
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
import javafx.scene.control.Alert;
import java.io.IOException;

public class logincontroller {

    private SmsSender smsSender;
    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;
    private UserService userService;
    private Stage stage;
    @FXML
    private ProgressIndicator loadingindicator;


    @FXML
    public void initialize() {
        userService = new UserService();
        smsSender = new SmsSender();
    }

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
                String loginResult = userService.loginUser(email, password);
                return loginResult == null;
            }
        };

        loginTask.setOnSucceeded(e -> {
            boolean loginSuccess = loginTask.getValue();
            loadingindicator.setVisible(false);

            if (loginSuccess) {
                userService.generateAndStoreVerificationCode(email);
                Object roleObj = SessionManager.getSession("role");
                if (roleObj != null && (int) roleObj != 1) {
                    // Redirect to 2FA page
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/2factor.fxml"));
                        Parent root = loader.load();
                        TwoFactorController twoFactorController = loader.getController();
                        twoFactorController.setEmail(email); // Pass email to 2FA controller
                        twoFactorController.setStage(new Stage()); // Set the stage for 2FA controller
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Two-Factor Authentication");
                        stage.show();
                        Stage currentStage = (Stage) emailLogin.getScene().getWindow();
                        currentStage.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Admin or Main user, handle accordingly
                    if (roleObj != null) {
                        int role = (int) roleObj;
                        if (role == -1) {
                            // Coach or player, navigate to profile
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
                                Parent root = loader.load();
                                ProfileController profileController = loader.getController();
                                profileController.setStage(new Stage()); // Set the stage for profile controller
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.setTitle("Profile");
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else if (role == 1) {

                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
                                emailLogin.getScene().setRoot(root);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            // Handle case where role is not defined
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("User role not defined. Please contact the administrator.");
                            alert.show();
                        }
                    }
                }
            } else {
                // Login unsuccessful
                Object roleObj = SessionManager.getSession("role");
                if (roleObj != null) {
                    String blockReason = userService.getBlockReason(email); // Assuming you have a method to fetch block reason
                    if (blockReason != null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Your account is blocked. Reason: " + blockReason);
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid email or password. Please try again.");
                        alert.show();
                    }
                }
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
    @FXML
    private void forgetpassword() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reserpasswordenteremail.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("reset password");

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage currentStage = (Stage) emailLogin.getScene().getWindow();
        currentStage.close();
    }


}
