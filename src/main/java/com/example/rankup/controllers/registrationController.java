package com.example.rankup.controllers;


import com.example.rankup.entities.User;
import com.example.rankup.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class registrationController {


    @FXML

 TextField usernamesignup;
    @FXML

    TextField emailsignup;
    @FXML
    PasswordField passwordsignup;
    @FXML
    DatePicker birthdatesignup;
    @FXML
    private PasswordField confirmpass;
    @FXML
    private ComboBox<String> rolelist;
    @FXML
    void initialize() {
        ObservableList<String> statutOptions = FXCollections.observableArrayList("player", "coach");
        rolelist.setItems(statutOptions);
    }

    @FXML
    public void signup(ActionEvent actionEvent) {


        String email = emailsignup.getText();
        String username = usernamesignup.getText();
        String password = passwordsignup.getText();
        String confirmPassword = confirmpass.getText();
        LocalDate birthdate = birthdatesignup.getValue();
        String selectedRole = rolelist.getValue();


        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || birthdate == null || selectedRole == null) {
            showErrorDialog("Please fill in all fields!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showErrorDialog("Passwords do not match!");
            return; // Stop further execution if passwords don't match
        }

        if (!isValidEmail(email)) {
            showErrorDialog("Invalid email format!");
            return;
        }
        if (password.length() < 8) {
            showErrorDialog("Password must be at least 8 characters long!");
            return;
        }
        String role;
        if ("player".equalsIgnoreCase(selectedRole)) {
            role = "[\"ROLE_PLAYER\"]";
        } else if ("coach".equalsIgnoreCase(selectedRole)) {
            role = "[\"ROLE_COACH\"]";
        } else {
            showErrorDialog("Invalid role selected!");
            return;
        }

        List<String> roles = new ArrayList<>();
        roles.add(role);
        User newUser = new User(email, username, password, birthdate, roles);


        // Call the registration service to register the new user
        boolean success = UserService.register(newUser);

        if (success) {
            // Registration successful, display a success message
            showSuccessDialog("User registration successful!");
        } else {
            // Registration failed, display an error message or handle it accordingly
            showErrorDialog("User registration failed!");
        }

    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }
    @FXML
    private void loginaction() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Profile");


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage currentStage = (Stage) emailsignup.getScene().getWindow();
        currentStage.close();
    }

    }
