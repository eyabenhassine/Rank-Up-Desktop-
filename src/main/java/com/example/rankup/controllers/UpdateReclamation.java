package com.example.rankup.controllers;

import com.example.rankup.entities.Reclamation;
import com.example.rankup.services.ReclamationService;
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
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateReclamation implements Initializable {
    @FXML
    private TextField numTelUpdate;
    @FXML
    private TextField descriptionUpdate;
    @FXML
    private TextField nomUpdate;
    @FXML
    private TextField typeUpdate;
    @FXML
    private TextField dateUpdate;

    private ReclamationService rec = new ReclamationService();
    private Reclamation selectedReclamation;

    private Reclamation selectedReclamaton ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize your controller here. For example:
        if (selectedReclamation != null) {
            setSelectedReclamation(selectedReclamation);
        }
    }


    public void setSelectedReclamation(Reclamation reclamation) {
        this.selectedReclamaton= reclamation;

        if (reclamation != null) {
            nomUpdate.setText(reclamation.getNom());
            numTelUpdate.setText(String.valueOf(reclamation.getNumTel()));
            dateUpdate.setText(reclamation.getDate());
            descriptionUpdate.setText(reclamation.getDescription());
            typeUpdate.setText(reclamation.getType());
        }
    }



    @FXML
    void modifierReclamation(ActionEvent event) {
        if (selectedReclamaton != null) {
            try {
                int numTel = Integer.parseInt(numTelUpdate.getText());
                String description = descriptionUpdate.getText();
                String nom = nomUpdate.getText();
                String date = dateUpdate.getText();
                String type = typeUpdate.getText(); // Utilisez `getValue()` pour obtenir la valeur sélectionnée

                // Vérifiez que les champs obligatoires ne sont pas vides
                if (nom.isEmpty() || date.isEmpty() || description.isEmpty() || type.isEmpty()) {
                    afficherAlerteErreur("Erreur de saisie", "Veuillez remplir tous les champs.");
                    return;
                }

                // Vérifiez la validité des données
              /*  if (numTel <= ) {
                    afficherAlerteErreur("Erreur de saisie", "Veuillez saisir des valeurs numériques positives.");
                    return;
                }*/

                // Mettre à jour les propriétés de `selectedReclamation`
                selectedReclamaton.setNumTel(numTel);
                selectedReclamaton.setNom(nom);
                selectedReclamaton.setDate(date);
                selectedReclamaton.setDescription(description);
                selectedReclamaton.setType(type);

                // Appeler la méthode de modification de `ReclamationService` ici
                rec.update(selectedReclamaton);

                afficherAlerteInformation("Modification réussie", "La réclamation a été modifiée avec succès !");
                showNotification("UPDATE réussie", " Recalamation modifié avec succès.");

                // Redirection vers la page de la liste des réclamations
                // Assurez-vous de modifier le chemin de fichier pour qu'il corresponde au fichier FXML de la liste des réclamations
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamation.fxml"));
                Scene scene = new Scene(root);

                // Obtenir l'étape actuelle à partir de l'événement ActionEvent
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Changer de scène
                stage.setScene(scene);
                stage.show();

            } catch (NumberFormatException | SQLException e) {
                afficherAlerteErreur("Erreur de saisie", "Veuillez saisir des valeurs numériques valides !");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            afficherAlerteErreur("Aucune réclamation sélectionnée", "Veuillez sélectionner une réclamation à modifier.");
        }
    }

    @FXML
    void naviguerAjout(ActionEvent event) {
        // Implémentez la navigation vers l'ajout si nécessaire
        try {
            // Charger la nouvelle scène à partir de votre fichier FXML cible
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterReclamation.fxml"));

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
        // Implémentez l'action de sortie si nécessaire
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            nomUpdate.getScene().setRoot(root);
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
    @FXML

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }
}
