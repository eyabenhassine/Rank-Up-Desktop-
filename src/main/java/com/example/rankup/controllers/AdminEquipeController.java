package com.example.rankup.controllers;

import com.example.rankup.entities.Equipe;
import com.example.rankup.entities.User;
import com.example.rankup.services.EquipeService;
import com.example.rankup.services.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class AdminEquipeController {
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
    EquipeService equipeService = new EquipeService();

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
        confirmButton.setOnAction(event -> handleEquipeAddition());
        rmUser1.setStyle("-fx-visited-color: #64b8b1;");
    }

    private void handleEquipeAddition() {
        if (nomEquipe.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Team name must not be empty");
            alert.showAndWait();
            return;
        }
        else if (!isSelectedUsersFull()) {
            System.out.println(isSelectedUsersFull());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Team is missing members");
            alert.showAndWait();
            return;
        }
        Equipe newEquipe = new Equipe(nomEquipe.getText());
        equipeService.ajouter(newEquipe);
        for (User u : selectedUsers) {
            u.setEquipe_id(newEquipe.getId());
            userService.modifierEquipe_id(u);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Team added successfully!");
        alert.showAndWait();
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

    Boolean isSelectedUsersFull() {
        for (User u : selectedUsers) { if (u == null) return false; }
        return true;
    }

    @FXML
    void clearSelectedUsers(ActionEvent event) {
        selectedUsers.replaceAll(user -> null);
        System.out.println(selectedUsers);
        for (TextFlow t : textFlows) { t.setVisible(false); }
    }

    @FXML
    void showEquipeList(ActionEvent event) {
//        System.out.println("Yaay equipe!!!!!!");
        // Create a new stage (popup window)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Choose Player");

        List<Equipe> equipeList = equipeService.getAllEquipesWithUsers();
        System.out.println(equipeList);

        // Create columns for the table
        TableColumn<Equipe, String> equipeNameColumn = new TableColumn<>("Equipe Name");
        equipeNameColumn.setCellValueFactory(cellData -> {
            Equipe equipe = cellData.getValue();
            String equipeName = equipe.getNom();
            return new SimpleStringProperty(equipeName);
        });

        TableColumn<Equipe, String> userNameColumn = new TableColumn<>("Members");
        userNameColumn.setCellValueFactory(cellData -> {
            Equipe equipe = cellData.getValue();
            List<User> users = equipe.getUsers();
            String userNames = users.stream()
                    .map(User::getUsername)
                    .collect(Collectors.joining(", "));
            System.out.println("userlist: " + userNames);
            return new SimpleStringProperty(userNames);
        });

        // Create the table and add columns
        TableView<Equipe> tableView = new TableView<>();

        // Create a TextField for searching
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        // Create a FilteredList to filter the data based on the search criteria
        FilteredList<Equipe> filteredData = new FilteredList<>(FXCollections.observableArrayList(equipeList), p -> true);

        // Bind the search field text property to the predicate of the filtered list
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipe -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all rows if the search field is empty
                }

                // Convert the search string to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if any column contains the search string
                if (equipe.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Match by equipe name
                }

                // Match by user names
                for (User user : equipe.getUsers()) {
                    if (user.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }

                return false; // No match found
            });
        });

        tableView.setItems(filteredData);
        tableView.getColumns().addAll(equipeNameColumn, userNameColumn);

        // Create content for the popup window
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());

        VBox popupLayout = new VBox();
        popupLayout.getChildren().addAll(searchField, tableView, closeButton);

        // Set the content in the popup window
        popupStage.setScene(new Scene(popupLayout, 300, 500));

        // Show the popup window
        popupStage.show();
    }

    public void logout(MouseEvent mouseEvent) {
    }

    @FXML
    void navToHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Profile");
            stage.showAndWait();
//            Stage currentStage = (Stage) walletq.getScene().getWindow();
//            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
