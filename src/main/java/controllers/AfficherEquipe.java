package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.Equipe;
import tn.esprit.services.EquipeService;

import java.sql.SQLException;
import java.util.List;

public class AfficherEquipe {

    @FXML
    private TableColumn<?, ?> nomEquipeCol;

    @FXML
    private TableColumn<?, ?> playersCol;

    @FXML
    private TableView<Equipe> tableView;

    private EquipeService equipeService = new EquipeService();

    @FXML
    void initialize() {
        try {
            List<Equipe> equipes = equipeService.recuperer();
            ObservableList<Equipe> observableList = FXCollections.observableList(equipes);
            tableView.setItems(observableList);

            nomEquipeCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
//            playersCol.setCellValueFactory(new PropertyValueFactory<>("players"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
