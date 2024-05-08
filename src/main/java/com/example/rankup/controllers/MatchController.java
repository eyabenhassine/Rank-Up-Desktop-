package com.example.rankup.controllers;

import com.example.rankup.entities.MatchEntity;
import com.example.rankup.entities.User;
import com.example.rankup.entities.Equipe;
import com.example.rankup.services.EquipeService;
import com.example.rankup.services.MatchService;
import com.example.rankup.services.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MatchController {
    @FXML
    private Button chooseEquipe2;

    @FXML
    private Button chooseEquipe1;

    @FXML
    private Button confirmButton;

    @FXML
    private Button resetButton;

    @FXML
    private Hyperlink rmUser1;

    @FXML
    private Hyperlink rmUser2;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private TextFlow textFlow1;

    @FXML
    private TextFlow textFlow2;
    @FXML
    private DatePicker datePicker = new DatePicker();

    private List<Text> texts = new ArrayList<>();

    private List<TextFlow> textFlows = new ArrayList<>();

    private List<Equipe> selectedEquipes;

    private MatchService matchService = new MatchService();
    private EquipeService equipeService = new EquipeService();

    @FXML
    public void initialize() {
        selectedEquipes = new ArrayList<>(Collections.nCopies(2, null));
        chooseEquipe1.setOnAction(event -> openPopup(0));
        chooseEquipe2.setOnAction(event -> openPopup(1));
        
        texts.add(text1);
        texts.add(text2);

        for (Text t : texts) {t.setText("None");}
        textFlows.add(textFlow1);
        textFlows.add(textFlow2);

        for (TextFlow f : textFlows) {f.setVisible(false);} // Invisible by default
        rmUser1.setOnAction(event -> handleVisibility(0));
        rmUser2.setOnAction(event -> handleVisibility(1));
        
        confirmButton.setOnAction(event -> handleMatchAddition());
        rmUser1.setStyle("-fx-visited-color: #64b8b1;");
    }

    private void handleMatchAddition() {
        System.out.println(datePicker.getValue());
        if (datePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a date");
            alert.showAndWait();
            return;
        }
        else if (!isSelectedEquipesFull()) {
            System.out.println(isSelectedEquipesFull());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Match is missing teams");
            alert.showAndWait();
            return;
        }

        MatchEntity newMatch = new MatchEntity(datePicker.getValue());
        matchService.ajouter(newMatch);
        for (Equipe e : selectedEquipes) {
            e.setMatch_id(newMatch.getId());
            equipeService.modifierMatch_id(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Team added successfully!");
        alert.showAndWait();
    }

    private boolean isSelectedEquipesFull() {
        for (Equipe e : selectedEquipes) { if (e == null) return false; }
        return true;
    }

    private void handleVisibility(int i) {
        selectedEquipes.set(i, null);
        textFlows.get(i).setVisible(false);
    }

    private void openPopup(int idx) {
        // Create a new stage (popup window)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Choose Equipe");

        List<Equipe> equipeList = equipeService.getAllEquipe();
        List<Equipe> availabeEquipes = getAvailableEquipes(equipeList);
        System.out.println(equipeList);
        System.out.println(availabeEquipes);
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
            System.out.println("userlist: "+userNames);
            return new SimpleStringProperty(userNames);
        });

        // Create the table and add columns
        TableView<Equipe> tableView = new TableView<>();
        tableView.setItems(FXCollections.observableArrayList(equipeList));
        tableView.getColumns().addAll(equipeNameColumn, userNameColumn);

        // Create content for the popup window
        Button okButton = new Button("OK");
        Button closeButton = new Button("Close");
        okButton.setOnAction(e -> {
            Equipe selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                handleSelectedValue(selectedItem, idx);
                System.out.println(selectedItem.getId());
                popupStage.close();
            }
        });
        closeButton.setOnAction(e -> popupStage.close());

        VBox popupLayout = new VBox();
        popupLayout.getChildren().addAll(tableView, okButton, closeButton);

        // Set the content in the popup window
        popupStage.setScene(new Scene(popupLayout, 300, 500));

        // Show the popup window
        popupStage.show();
    }

    private void handleSelectedValue(Equipe selectedItem, int idx) {
        selectedEquipes.set(idx, selectedItem);
        System.out.println(selectedEquipes);
        if (selectedEquipes.get(idx) != null) {
            texts.get(idx).setText(selectedEquipes.get(idx).getNom());
            textFlows.get(idx).setVisible(true);
        }
        else {
            texts.get(idx).setText("None");
            textFlows.get(idx).setVisible(false);
        }
    }

    private List<Equipe> getAvailableEquipes(List<Equipe> equipeList) {
        List<Equipe> newEquipe = new ArrayList<>(equipeList);
        newEquipe.add(null); // FIX THIS
        System.out.println("removing: ");
        System.out.println(selectedEquipes);
        System.out.println("from");
        System.out.println(newEquipe);
        newEquipe.removeAll(selectedEquipes);
        System.out.println("result: ");
        System.out.println(newEquipe);
        return newEquipe;
    }

    @FXML
    void clearSelectedUsers(ActionEvent event) {

    }

    @FXML
    void logout(MouseEvent event) {

    }

    @FXML
    void showMatchList(ActionEvent event) {

    }

//    @FXML
//    void testdatepicker(ActionEvent event) {
//        System.out.println(datePicker.getValue());
//    }
}
