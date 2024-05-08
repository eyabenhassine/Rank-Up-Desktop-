package org.example.rankup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.rankup.entities.Categorie;
import org.example.rankup.services.CategorieService;

import java.io.IOException;
import java.sql.SQLException;

public class updateCategorie {

    @FXML
    private TextField nom_categorie;

    @FXML
    private TextField type;

    private Categorie selectedCategorie;
    private CategorieService service = new CategorieService();

    public void setSelectedCategorie(Categorie categorie) {
        this.selectedCategorie = categorie;

        if (categorie != null) {
            nom_categorie.setText(categorie.getNom_categorie());
            type.setText(categorie.getType());
        }
    }
    // Méthode pour valider la longueur du champ "nom"
    private boolean validateNomLength(String nom) {
        return nom.length() <= 8;
    }

    // Méthode pour valider le champ "type"
    private boolean validateType(String type) {
        return type.equalsIgnoreCase("image") || type.equalsIgnoreCase("video");
    }


    @FXML
    void updateCategorie(ActionEvent event) {
        System.out.println("Update Categorie button clicked");
        String nom = nom_categorie.getText();
        String type = this.type.getText();

        if (selectedCategorie != null) {
            try {
                // Vérifier que les champs obligatoires ne sont pas vides
                if (!(!nom.isEmpty() || !type.isEmpty())) {
                    showAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
                    return;
                }

                // Vérifier que la longueur du champ "nom" est inférieure ou égale à 8 caractères
                if (!validateNomLength(nom)) {
                    showAlert("Erreur de saisie", "Le champ 'nom' ne peut pas dépasser 8 caractères.");
                    return;
                }

                // Vérifier que le champ "type" est soit "image" ou "video"
                if (!validateType((type))) {
                    showAlert("Erreur de saisie", "Le champ 'type' doit être 'image' ou 'video'.");
                    return;
                }


                // Vérifier que les champs obligatoires ne sont pas vides
                if (nom.isEmpty() || type.isEmpty()) {
                    showAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
                    return;
                }

                // Mettre à jour les propriétés de `selectedCategorie`
                selectedCategorie.setNom_categorie(nom);
                selectedCategorie.setType(type);

                // Appeler la méthode de modification de `CategorieService` ici
                service.update(selectedCategorie);

                showAlert("Modification réussie", "La catégorie a été modifiée avec succès !");
            } catch (SQLException e) {
                showAlert("Erreur de base de données", "Une erreur est survenue lors de la mise à jour de la catégorie: " + e.getMessage());
            }
        } else {
            showAlert("Aucune catégorie sélectionnée", "Veuillez sélectionner une catégorie à modifier.");
        }
    }

    @FXML
    void naviguerE(ActionEvent event) {
        try {
            System.out.println("Navigating back...");
            Parent root = FXMLLoader.load(getClass().getResource("/afficheCategorie.fxml"));
            nom_categorie.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException("Error navigating back: " + e.getMessage(), e);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
