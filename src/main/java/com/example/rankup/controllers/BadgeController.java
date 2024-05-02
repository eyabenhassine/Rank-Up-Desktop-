package com.example.rankup.controllers;



import com.example.rankup.services.BadgeService;
import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
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
    public void purchaseBronze(ActionEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Bronze", badgeService);
        if (purchased) {
            // Close the current stage upon successful purchase
            stage.close();
        } else {
            // Handle failed purchase
        }
    }


    @FXML
    public void purchaseGold(ActionEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Gold", badgeService);
        if (purchased) {
            // Close the current stage upon successful purchase
            stage.close();
        } else {
            // Handle failed purchase
        }
    }

    @FXML
    public void purchaseSilver(ActionEvent actionEvent) {
        int userId = (int) SessionManager.getSession("userId");
        boolean purchased = userService.purchaseBadge(userId, "Silver", badgeService);
        if (purchased) {
            // Close the current stage upon successful purchase
            stage.close();
        } else {
            // Handle failed purchase
        }
    }

}

