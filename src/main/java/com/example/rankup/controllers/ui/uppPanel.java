package com.example.rankup.controllers.ui;

import com.example.rankup.entities.User;
import com.example.rankup.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class uppPanel {
    @FXML
    private Text walletq;
    private Stage stage;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
        fetchUserData();
    }

    private void fetchUserData() {
        //        User user = userService.fetchPlayerData();
        User user = null;
        if (user != null) {


            walletq.setText(String.valueOf(user.getWallet()));


        } else {


            walletq.setText("N/A");

        }
    }

    @FXML
    public void navToProfile(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Profile");
            stage.showAndWait();
            Stage currentStage = (Stage) walletq.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(MouseEvent event) {

//        SessionManager.clearSession();


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
            Stage currentStage = (Stage) walletq.getScene().getWindow();
            currentStage.close();

            // Show the login stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navToBadges(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/badge.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Badges");
            stage.show(); // Use show() instead of showAndWait()

            // Close the current stage immediately
            Stage currentStage = (Stage) walletq.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
