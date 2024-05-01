package com.example.rankup.controllers;

import com.example.rankup.entities.User;
import com.example.rankup.services.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class EquipeController {
    @FXML
    private Button choosePlayer1;

    @FXML
    private Button choosePlayer2;

    @FXML
    private Button choosePlayer3;

    @FXML
    private Button choosePlayer4;

    @FXML
    private Button choosePlayer5;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private Text text3;

    @FXML
    private Text text4;

    @FXML
    private Text text5;

    @FXML
    private TextFlow textFlow1;

    @FXML
    private TextFlow textFlow2;

    @FXML
    private TextFlow textFlow3;

    @FXML
    private TextFlow textFlow4;

    @FXML
    private TextFlow textFlow5;
    @FXML
    private Hyperlink rmUser1;

    @FXML
    private Hyperlink rmUser2;

    @FXML
    private Hyperlink rmUser3;

    @FXML
    private Hyperlink rmUser4;

    @FXML
    private Hyperlink rmUser5;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField nomEquipe;

    @FXML
    private Button resetButton;

    private List<Text> texts = new ArrayList<>();

    private List<TextFlow> textFlows = new ArrayList<>();

    private List<User> selectedUsers;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        selectedUsers = new ArrayList<>(Collections.nCopies(5, null));
        choosePlayer1.setOnAction(event -> openPopup(0));
        choosePlayer2.setOnAction(event -> openPopup(1));
        choosePlayer3.setOnAction(event -> openPopup(2));
        choosePlayer4.setOnAction(event -> openPopup(3));
        choosePlayer5.setOnAction(event -> openPopup(4));
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        texts.add(text5);
        for (Text t : texts) {t.setText("None");}
        textFlows.add(textFlow1);
        textFlows.add(textFlow2);
        textFlows.add(textFlow3);
        textFlows.add(textFlow4);
        textFlows.add(textFlow5);
        for (TextFlow f : textFlows) {f.setVisible(false);} // Invisible by default
        rmUser1.setOnAction(event -> handleVisibility(0));
        rmUser2.setOnAction(event -> handleVisibility(1));
        rmUser3.setOnAction(event -> handleVisibility(2));
        rmUser4.setOnAction(event -> handleVisibility(3));
        rmUser5.setOnAction(event -> handleVisibility(4));
        rmUser1.setStyle("-fx-visited-color: #64b8b1;");
    }

    private void handleVisibility(int i) {
        selectedUsers.set(i, null);
        textFlows.get(i).setVisible(false);
    }

    private void openPopup(int idx) {
        // Create a new stage (popup window)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Choose Player");

        List<User> userList = userService.getAllUsers();
        List<User> availableUsers = getAvailableUsers(userList);
        System.out.println(availableUsers);
        System.out.println(selectedUsers);
        ListView<User> listView = new ListView<>(FXCollections.observableArrayList(availableUsers));

        // Create content for the popup window
        Button okButton = new Button("OK");
        Button closeButton = new Button("Close");
        okButton.setOnAction(e -> {
            User selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                handleSelectedValue(selectedItem, idx);
                System.out.println(selectedItem.getId());
                popupStage.close();
            }
        });
        closeButton.setOnAction(e -> popupStage.close());

        VBox popupLayout = new VBox();
        popupLayout.getChildren().addAll(listView, okButton, closeButton);

        // Set the content in the popup window
        popupStage.setScene(new Scene(popupLayout, 300, 500));

        // Show the popup window
        popupStage.show();
    }

    private List<User> getAvailableUsers(List<User> userList) {
        List<User> newUser = new ArrayList<>(userList);
        newUser.removeAll(selectedUsers);
        return newUser;
    }

    private void handleSelectedValue(User selectedItem, int idx) {
        selectedUsers.set(idx, selectedItem);
        System.out.println(selectedUsers);
        if (selectedUsers.get(idx) != null) {
            texts.get(idx).setText(selectedUsers.get(idx).getUsername());
            textFlows.get(idx).setVisible(true);
        }
        else {
            texts.get(idx).setText("None");
            textFlows.get(idx).setVisible(false);
        }
    }

    public void logout(MouseEvent mouseEvent) {
    }
}
