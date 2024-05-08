package com.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.rankup.entities.lecon;
import com.example.rankup.services.leconService;

import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;

public class updatelecon implements Initializable {

    @FXML
    private TextField nom_lecon;

    @FXML
    private TextField URL;

    @FXML
    private TextField prix;

    @FXML
    private TextField descriptionUpdate;

    private leconService leconService = new leconService();
    private lecon selectedLecon;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize your controller here. For example:
        if (selectedLecon != null) {
            setSelectedLecon(selectedLecon);
        }
    }
    public void setSelectedLecon(lecon lecon) {
        this.selectedLecon = lecon;

        if (lecon != null) {
            nom_lecon.setText(lecon.getNom_lecon());
            URL.setText(lecon.getUrl());
            prix.setText(String.valueOf(lecon.getPrix()));
            descriptionUpdate.setText(lecon.getDescription());
        }
    }

    @FXML
    void modifier_lecon(ActionEvent event) {
        System.out.println("Modifier leçon button clicked"); // Ajouter un message de débogage

        if (selectedLecon != null) {
            try {
                String nom = nom_lecon.getText();
                String url = URL.getText();
                String prixStr = prix.getText();
                String description = descriptionUpdate.getText();

                // Afficher les valeurs des champs de texte pour vérification
                System.out.println("Nom: " + nom);
                System.out.println("URL: " + url);
                System.out.println("Prix: " + prixStr);
                System.out.println("Description: " + description);

                // Vérifier que les champs obligatoires ne sont pas vides
                if (nom.isEmpty() || url.isEmpty() || prixStr.isEmpty() || description.isEmpty()) {
                    afficherAlerteErreur("Erreur de saisie", "Veuillez remplir tous les champs.");
                    return;
                }

                // Vérifier que le prix est un nombre valide et non négatif
                double prix;
                try {
                    prix = Double.parseDouble(prixStr);
                    if (prix < 0) {
                        afficherAlerteErreur("Erreur de saisie", "Le prix ne peut pas être négatif.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    afficherAlerteErreur("Erreur de saisie", "Veuillez saisir un prix valide.");
                    return;
                }

                // Mettre à jour les propriétés de `selectedLecon`
                selectedLecon.setNom_lecon(nom);
                selectedLecon.setUrl(url);
                selectedLecon.setPrix((int) prix);
                selectedLecon.setDescription(description);

                // Afficher les valeurs de la leçon après la mise à jour
                System.out.println("Leçon après mise à jour: " + selectedLecon);

                // Appeler la méthode de modification de `LeconService` ici
                leconService.update(selectedLecon);

                afficherAlerteInformation("Modification réussie", "La leçon a été modifiée avec succès !");
            } catch (SQLException e) {
                afficherAlerteErreur("Erreur de base de données", "Une erreur est survenue lors de la mise à jour de la leçon: " + e.getMessage());
            }
        } else {
            afficherAlerteErreur("Aucune leçon sélectionnée", "Veuillez sélectionner une leçon à modifier.");
        }
    }
    @FXML
    void naviguerL(ActionEvent event) {
        // Implémentez la navigation vers l'ajout si nécessaire
        try {
            // Charger la nouvelle scène à partir de votre fichier FXML cible
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterlecon.fxml"));

            // Obtenir la fenêtre (Stage) actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            // Gérer les exceptions, par exemple en affichant un message d'erreur
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerExit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherlecon.fxml"));
            nom_lecon.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAlerteInformation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
