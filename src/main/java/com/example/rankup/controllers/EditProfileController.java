package com.example.rankup.controllers;

import com.example.rankup.entities.User;
import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class EditProfileController {

    @FXML
    private TextField firstnameq;
    @FXML
    private TextField lastnameq;
    @FXML
    private TextField usernameq;
    @FXML
    private TextField phoneq;
    @FXML
    private DatePicker birthdayq;
    @FXML
    private TextField summnameq;
    @FXML
    private TextArea bioq;
    @FXML
    private ChoiceBox<String> eloq;
    @FXML
    private Text errorText;
    private Stage stage;

    private UserService userService;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void initialize() {
        userService = new UserService();
        loadUserData();
        ObservableList<String> eloOptions = FXCollections.observableArrayList("bronze", "silver");
        eloq.setItems(eloOptions);
    }

    private void loadUserData() {
        User user = userService.fetchPlayerData();
        if (user != null) {
            firstnameq.setText(user.getFirstname());
            lastnameq.setText(user.getLastname());
            usernameq.setText(user.getUsername());
            phoneq.setText(user.getPhone());
            birthdayq.setValue(user.getBirthdate());
            summnameq.setText(user.getSummonername());
            bioq.setText(user.getBio());
            eloq.setValue(user.getElo());
        } else {
            errorText.setText("Failed to load user data");
        }
    }
    @FXML
    private void closeDialog() {
        // Close the current dialog
        stage.close();
    }

    @FXML
    void saveChanges() {
        String firstname = firstnameq.getText();
        String lastname = lastnameq.getText();
        String username = usernameq.getText();
        String phone = phoneq.getText();
        String summonername = summnameq.getText();
        String bio = bioq.getText();
        String elo = eloq.getValue();

        if (!phone.matches("\\d{8}")) {
            errorText.setText("Phone number must be 8 digits");
            return;
        }


        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || phone.isEmpty() || birthdayq.getValue() == null || summonername.isEmpty() || bio.isEmpty() || elo == null) {
            errorText.setText("Please fill in all fields");
            return;
        }
        User updatedUser = new User(firstname, lastname, username, phone, bio, summonername, birthdayq.getValue(), elo);
        boolean success = userService.editUserInfo(updatedUser);
        if (success) {
            stage.close();
            showSuccessDialog();
            // Navigate back to profile page
            navigateToProfilePage();
        } else {
            errorText.setText("Failed to update profile");
        }

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

            // Close the current profile window
            Stage currentStage = (Stage) firstnameq.getScene().getWindow();
            currentStage.close();

            // Show the login stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Profile updated successfully");
        alert.showAndWait();
    }

    private void navigateToProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            Stage profileStage = new Stage();
            profileStage.setScene(scene);
            profileStage.setTitle("User Profile");

            // Close the current stage
            stage.close();

            // Show the profile stage
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
