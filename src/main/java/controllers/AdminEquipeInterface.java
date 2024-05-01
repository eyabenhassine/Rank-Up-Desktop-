package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import tn.esprit.models.Equipe;
import tn.esprit.services.EquipeService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminEquipeInterface implements Initializable {

    @FXML
    private TextField TextDepart;

    @FXML
    private TextField TextArrivee;

    @FXML
    private Button AjouterID;

    @FXML
    private TableColumn<?, ?> DepartCol;

    @FXML
    private TableColumn<?, ?> IDCol;

    @FXML
    private Button ModifierID;

    @FXML
    private TableColumn<?, ?> NomCol;

    @FXML
    private Button SupprimerID;

    @FXML
    private TextField TextNom;
    private String newname;

    @FXML
    private TextField TextRecherche;

    @FXML
    private ImageView circuit;

    @FXML
    private TextField ifid;

    @FXML
    private Button rechercheID;

    @FXML
    private TableView<Equipe> table;

    private EquipeService equipeService = new EquipeService();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Equipe> equipes = null;
        try {
            equipes = equipeService.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Equipe> observableList = FXCollections.observableList(equipes);
        table.setItems(observableList);

        NomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
    }

    @FXML
    private void AjouterAction(ActionEvent event) {
//
        if (TextNom.getText().equals("")) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("les champs sont obligatoire !");
            alert.showAndWait();
        }else{
            Equipe c = new Equipe();
            c.setNom(TextNom.getText());
            try {
                equipeService.ajouter(c);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            reset();
            refresh();

        }
    }
//
//
//
//
//

    @FXML
    private void SupprimerAction(ActionEvent event) throws SQLException {
        if (table.getSelectionModel().getSelectedItem() == null ){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("s'il vous plait selectionnez un equipe!");
            alert.showAndWait();
        }else{

            System.out.println( table.getSelectionModel().getSelectedItem()) ;
            System.out.println("test1");
            System.out.println( TextNom.getText()) ;
            equipeService.supprimer(TextNom.getText());
            refresh();
        }
//
    }
//
    @FXML
    private void ModifierAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem()== null ){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("s'il vous plait selectionnez un circuit !");
            alert.showAndWait();
        }else{
            Equipe e = new Equipe();
            e.setNom(TextNom.getText());
            String nom= e.getNom();
            try {
                equipeService.modifier(e, newname);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            reset();
            refresh();
        }
    }
//
    private void reset() {//
        TextNom.setText("");
    }
//
    @FXML
    public void getSelected(MouseEvent event){
        int index = -1;
        index = table.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
//        ifid.setText(IDCol.getCellData(index).toString());
        TextNom.setText(NomCol.getCellData(index).toString());
        newname = TextNom.getText();
        //TestDep.setText(DepartCol.getCellData(index).toString());
        //testArr.setText(ArriveeCol.getCellData(index).toString());
    }
//
//
//
    private void refresh(){
        List<Equipe> equipes = null;
        try {
            equipes = equipeService.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Equipe> observableList = FXCollections.observableList(equipes);
        table.setItems(observableList);

        NomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        table.setItems(observableList);
    }
//
    @FXML
    private void Rechercher(KeyEvent event) {
//        String nom1 = "";
//        if (event.getText().length()>0)
//            nom1 = TextRecherche.getText()+ event.getText();
//        else
//            nom1 = TextRecherche.getText().substring(0,TextRecherche.getText().length()-1 );
//        System.out.println(nom1);
//        String nom = nom1.toLowerCase();
//        ObservableList<Equipe> list =  listCir.stream()
//                .filter(r -> r.getNomC().toLowerCase().contains(nom)).collect(Collectors.toCollection(FXCollections::observableArrayList));
//        table.setItems(list);
//
    }
//
    @FXML
    private void fresh(MouseEvent event) {
        List<Equipe> equipes = null;
        try {
            equipes = equipeService.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Equipe> observableList = FXCollections.observableList(equipes);
        table.setItems(observableList);

        NomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

    }


}


