package com.example.rankup.controllers;

import com.example.rankup.entities.User;
import com.example.rankup.services.SessionManager;
import com.example.rankup.services.UserService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UsersListController {

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> idusercol;

    @FXML
    private TableColumn<User, String> usernamecol;

    private UserService userService;

    public void initialize() {
        userService = new UserService();
        populateTableView();
    }

    private void populateTableView() {
        List<User> userList = userService.getAllUsers();
        ObservableList<User> observableList = FXCollections.observableArrayList(userList);
        tableView.setItems(observableList);

        idusercol.setCellValueFactory(cellData -> {
            int id = cellData.getValue().getId();
            return new SimpleIntegerProperty(id).asObject();
        });
        usernamecol.setCellValueFactory(cellData -> {
            String username = cellData.getValue().getUsername();
            return new SimpleStringProperty(username);
        });
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                User selectedUser = tableView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    openUserDetails(selectedUser.getId());
                }
            }
        });
    }
    private void openUserDetails(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminedituser.fxml"));
            Parent root = loader.load();

            UserDetailsController userDetailsController = loader.getController();
            userDetailsController.setUserId(userId);
            userDetailsController.setUserService(userService);
            userDetailsController.populateUserDetails(userId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            // Access the scene from the tableView
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
