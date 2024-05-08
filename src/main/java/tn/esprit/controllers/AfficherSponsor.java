package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import tn.esprit.entities.Sponsor;
import tn.esprit.services.SponsorService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherSponsor {
    @FXML
    private TableColumn<Sponsor, String> NomSponsorCol;

    @FXML
    private TableColumn<Sponsor, String>  AdresseSponsorCol;

    @FXML
    private TableColumn<Sponsor, String>  MailSponsorCol;

    @FXML
    private TableView<Sponsor> tableView;

    private final SponsorService sp;

    {
        try {
            sp = new SponsorService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        try {
            // Récupérer la liste des événements à partir de la méthode afficherSponsors()
            List<Sponsor> sponsors = sp.afficherSponsors();
            System.out.println("Event loaded: " + sponsors);

            // Créer une ObservableList à partir de la liste d'événements
            ObservableList<Sponsor> observableList = FXCollections.observableList(sponsors);

            // Définir les éléments de la TableView avec la liste observable
            tableView.setItems(observableList);

            // Configuration des PropertyValueFactory pour chaque colonne de la TableView
            NomSponsorCol.setCellValueFactory(new PropertyValueFactory<>("nom_sponsor"));
            AdresseSponsorCol.setCellValueFactory(new PropertyValueFactory<>("adresse_sponsor"));
            MailSponsorCol.setCellValueFactory(new PropertyValueFactory<>("mail_sponsor"));


            // Actualiser la TableView pour refléter les changements
            tableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Échec de l'initialisation de la TableView : " + e.getMessage());
            alert.showAndWait();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void deleteSponsor(ActionEvent event) {
        Sponsor selectedSponsor = tableView.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            try {
                sp.deleteSponsor(selectedSponsor.getId());
                // Appeler la méthode deleteEvent() pour supprimer l'événement sélectionné
                //deleteEvent(selectedEvent.getId());
                // Mettre à jour la TableView en supprimant l'événement sélectionné de la liste des éléments
                tableView.getItems().remove(selectedSponsor);
                showAlert("Suppression réussie", "L'événement a été supprimé avec succès.");
                //showNotification("Opération réussie", "Evenement a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la suppression de l'événement : " + e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un événement à supprimer.");
        }
    }



    public void naviguerAddS(ActionEvent event) {
    }

    public void modifierSponsor(ActionEvent event) {
        Sponsor selectedSponsor = tableView.getSelectionModel().getSelectedItem();

        if (selectedSponsor != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSponsor.fxml"));
                Parent root = loader.load();

                ModifierSponsor controller = loader.getController();
                controller.setSelectedSponsor(selectedSponsor);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur de chargement", "Impossible de charger la page de modification du sponsor.");
            }
        } else {
            System.out.println("Aucun sponsor sélectionné.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sélection manquante");
            alert.setContentText("Veuillez sélectionner un sponsor à modifier.");
            alert.showAndWait();
        }
    }



}
