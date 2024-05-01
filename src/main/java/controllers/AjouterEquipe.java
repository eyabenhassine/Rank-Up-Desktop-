package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Equipe;
import tn.esprit.services.EquipeService;
import tn.esprit.services.PlayersService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterEquipe {
    @FXML
    private ComboBox<String> fifthPlayer;

    @FXML
    private ComboBox<String> firstPlayer;

    @FXML
    private ComboBox<String> fourthPlayer;

    @FXML
    private TextField nomEquipeTF;

    @FXML
    private ComboBox<String> secondPlayer;

    @FXML
    private ComboBox<String> thirdPlayer;

    private final EquipeService equipeService = new EquipeService();

    public void initialize() throws SQLException {
        // Add more items to the ComboBox dynamically if needed
        PlayersService ps = new PlayersService();
        List<String> currPlayersList = ps.getPlayers();

        firstPlayer.getItems().addAll(currPlayersList);
        secondPlayer.getItems().addAll(currPlayersList);
        thirdPlayer.getItems().addAll(currPlayersList);
        fourthPlayer.getItems().addAll(currPlayersList);
        fifthPlayer.getItems().addAll(currPlayersList);

        // Add event handler for selection changes if needed
        firstPlayer.setOnAction(event -> {
            String selectedOption = firstPlayer.getValue();
            System.out.println("Selected Option: " + selectedOption);
        });
        secondPlayer.setOnAction(event -> {
            String selectedOption = secondPlayer.getValue();
            System.out.println("Selected Option: " + selectedOption);
        });
        thirdPlayer.setOnAction(event -> {
            String selectedOption = thirdPlayer.getValue();
            System.out.println("Selected Option: " + selectedOption);
        });
        fourthPlayer.setOnAction(event -> {
            String selectedOption = fourthPlayer.getValue();
            System.out.println("Selected Option: " + selectedOption);
        });
        fifthPlayer.setOnAction(event -> {
            String selectedOption = fifthPlayer.getValue();
            System.out.println("Selected Option: " + selectedOption);
        });
    }

    public void ajouter(ActionEvent actionEvent) {
        try {
            if (nomEquipeTF.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Nom ne doit etre pas vide!");
                alert.showAndWait();
                return;
            }
            equipeService.ajouter(new Equipe( nomEquipeTF.getText() ));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Equipe ajoutee avec succes!");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void naviguer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipe.fxml"));
            Parent root = loader.load();
            nomEquipeTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
