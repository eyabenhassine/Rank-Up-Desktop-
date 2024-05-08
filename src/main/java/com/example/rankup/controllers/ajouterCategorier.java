package com.example.rankup.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.example.rankup.entities.Categorie;
import com.example.rankup.services.CategorieService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ajouterCategorier {

    public Button uploadImg;
    public Button ajouterCategorie;
    @FXML
    private TextField nom_categorie;

    @FXML
    private TextField type;

    @FXML
    private ImageView imageView;
    private String imageUrl;

    private final CategorieService lc = new CategorieService();

    private final FileChooser fileChooser = new FileChooser();
    @FXML
    void initialize() {
        // Initialisation du file chooser pour sélectionner une image
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg")
        );

        // Définition d'une action pour le bouton "Choisir une image"
        uploadImg.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                imageUrl = selectedFile.toURI().toString();
                // Ajouter le préfixe "file:/" si ce n'est pas déjà le cas
                if (imageUrl.startsWith("file:\\")) {
                    imageUrl = "file:/" + imageUrl.substring(6);
                }
                Image image = new Image(imageUrl);
                imageView.setImage(image);
            }
        });

        // Définition d'une action pour le bouton "Ajouter Catégorie"
        ajouterCategorie.setOnAction(event -> {
            try {
                String nomCategorie = nom_categorie.getText();
                String typeCategorie = type.getText();

                // Vérification de la longueur du nom de catégorie
                if (nomCategorie.isEmpty() || nomCategorie.length() > 8) {
                    afficherAlerteErreur("Erreur de saisie", "Le nom de la catégorie doit avoir entre 1 et 8 caractères.");
                    return;
                }

                // Vérification du type de catégorie
                if (!typeCategorie.equalsIgnoreCase("image") && !typeCategorie.equalsIgnoreCase("video") && !typeCategorie.equalsIgnoreCase("youtube")) {
                    afficherAlerteErreur("Erreur de saisie", "Le type de la catégorie doit être 'image', 'video' ou 'youtube'.");
                    return;
                }

                // Vérification de la sélection d'une image
                if (imageUrl == null) {
                    afficherAlerteErreur("Erreur de saisie", "Veuillez sélectionner une image.");
                    return;
                }

                // Création de la nouvelle catégorie et ajout à la base de données
                Categorie newCategorie = new Categorie(nomCategorie, typeCategorie, imageUrl);
                lc.add(newCategorie);
                afficherAlerteInformation("Succès", "La catégorie a été ajoutée avec succès !");
                // Effacer les champs et réinitialiser l'image
                nom_categorie.clear();
                type.clear();
                imageView.setImage(null);
                imageUrl = null;
            } catch (SQLException e) {
                afficherAlerteErreur("Erreur", "Une erreur est survenue lors de l'ajout de la catégorie : " + e.getMessage());
            }
        });
    }
    // Méthode pour afficher une alerte d'erreur
    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher une alerte d'information
    private void afficherAlerteInformation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void naviguerCategorie(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheCategorie.fxml"));
            Parent root = loader.load();

            // Passer l'URL de l'image sélectionnée à afficheCategorie
            afficheCategorie controller = loader.getController();
            controller.setSelectedImage(imageView);

            nom_categorie.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
