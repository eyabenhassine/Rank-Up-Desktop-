package com.example.rankup.controllers;

import com.example.rankup.entities.User;
import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ProfileController {


    @FXML
    private Text nameq;

    @FXML
    private Text lastnameq;

    @FXML
    private Text phoneq;

    @FXML
    private Text birthdayq;

    @FXML
    private Text eloq;

    @FXML
    private Text usernameq;

    @FXML
    private Text emailq;

    @FXML
    private Text bioq;

    @FXML
    private Text sumnameq;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setUserId(int userId) {

    }
    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
//        fetchUserData();
    }

    private void fetchUserData() {

        User user = userService.fetchPlayerData();
        if (user != null) {


            nameq.setText(user.getFirstname());
            lastnameq.setText(user.getLastname());
            phoneq.setText(user.getPhone());
            usernameq.setText(user.getUsername());
            emailq.setText(user.getEmail());
            birthdayq.setText(user.getBirthdate().toString());
            eloq.setText(user.getElo());
            bioq.setText(user.getBio());
            sumnameq.setText(user.getSummonername());


        } else {
            // Handle case where user data is not found

            nameq.setText("N/A");
            lastnameq.setText("N/A");
            phoneq.setText("N/A");
            birthdayq.setText("N/A");
            emailq.setText("N/A");
            usernameq.setText("N/A");
            eloq.setText("N/A");
            bioq.setText("N/A");
            sumnameq.setText("N/A");
        }
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
    @FXML
    private void logout() {

        SessionManager.clearSession();


        try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");

            // Close the current profile window
            Stage currentStage = (Stage) usernameq.getScene().getWindow();
            currentStage.close();

            // Show the login stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void deleteaccount() {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete your account? This action cannot be undone.");

        // Wait for user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed deletion, proceed with deleting the account
            int userId = (int) SessionManager.getSession("userId");
            if (userService.deleteUserAccount(userId)) {
                // Account deleted successfully, logout
                logout();
            } else {
                // Failed to delete account, show error message
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to delete your account. Please try again later.");
                errorAlert.showAndWait();
            }
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
            stage.setTitle("home");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage currentStage = (Stage) nameq.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void changepass() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/changepass.fxml"));
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

        Stage currentStage = (Stage) nameq.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToEditProfile() {
        try {
            // Load the EditProfile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editprofile.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            Stage editProfileStage = new Stage();
            editProfileStage.setScene(scene);
            editProfileStage.setTitle("Edit Profile");

            // Pass the stage reference to the EditProfileController
            EditProfileController editProfileController = loader.getController();
            editProfileController.setStage(editProfileStage);

            // Close the current dialog
            Stage currentStage = (Stage) nameq.getScene().getWindow();
            currentStage.close();

            // Show the Edit Profile stage
            editProfileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
