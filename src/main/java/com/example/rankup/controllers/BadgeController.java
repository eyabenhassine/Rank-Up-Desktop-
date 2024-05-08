package com.example.rankup.controllers;

import com.example.rankup.services.BadgeService;
import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class BadgeController {
    @FXML
    private Text bronzePrice;
    @FXML
    private Text GoldPrice;
    @FXML
    private Text silverPrice;
    private UserService userService;
    private BadgeService badgeService;
    @FXML
    public void initialize() {
        userService = new UserService();
        badgeService = new BadgeService();
        displayBadgePrices();
    }

    private void displayBadgePrices() {

        badgeService.displayAllBadges();

        int bronzeBadgePrice = badgeService.getBadgePrice("Bronze");
        int goldBadgePrice = badgeService.getBadgePrice("Gold");
        int silverBadgePrice = badgeService.getBadgePrice("Silver");

        bronzePrice.setText(String.valueOf(bronzeBadgePrice));
        GoldPrice.setText(String.valueOf(goldBadgePrice));
        silverPrice.setText(String.valueOf(silverBadgePrice));
    }

    @FXML
    public void purchaseBronze(MouseEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Bronze", badgeService);
        if (purchased) {
            showPurchaseConfirmation("Bronze");
        } else {
            showInsufficientFundsAlert();
        }
    }


    @FXML
    public void purchaseGold(MouseEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Gold",badgeService);
        if (purchased) {
            showPurchaseConfirmation("Gold");
        } else {
            showInsufficientFundsAlert();
        }
    }

    @FXML
    public void purchaseSilver(MouseEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Silver",badgeService);
        if (purchased) {
            showPurchaseConfirmation("Silver");
        } else {
            showInsufficientFundsAlert();
        }
    }
    private void showPurchaseConfirmation(String badgeName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Badge Purchase");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! You have purchased the " + badgeName + " badge.");
        alert.showAndWait();
    }
    private void showInsufficientFundsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Badge Purchase");
        alert.setHeaderText(null);
        alert.setContentText("Sorry, there is not enough money left in your wallet.");
        alert.showAndWait();
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

        Stage currentStage = (Stage) GoldPrice.getScene().getWindow();
        currentStage.close();
    }

}
