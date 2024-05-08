package com.example.rankup.controllers;


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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.controlsfx.control.Notifications;
import com.example.rankup.entities.lecon;
import com.example.rankup.services.leconService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class afficherlecon {

    public TextField chercherlecon;
    @FXML
    private TableColumn<lecon, String> nom_lecon;

    @FXML
    private TableColumn<lecon, String> url;

    @FXML
    private TableColumn<lecon, Integer> prix;

    @FXML
    private TableColumn<lecon, String> description;

    @FXML
    private TableView<lecon> tableView;

    private final leconService rec = new leconService();

    @FXML
    void initialize() {

        try {
            List<lecon> lecons = rec.afficherRec();
            System.out.println("lecons loaded: " + lecons);

            ObservableList<lecon> observableList = FXCollections.observableList(lecons);
            tableView.setItems(observableList);

            nom_lecon.setCellValueFactory(new PropertyValueFactory<>("nom_lecon"));
            url.setCellValueFactory(new PropertyValueFactory<>("url"));
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));

            tableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to initialize the TableView: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ajouterlecon.fxml")));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void naviguerUpdate(ActionEvent event) {
        // Obtenez la leçon sélectionnée dans la TableView
        lecon selectedLecon = tableView.getSelectionModel().getSelectedItem();

        if (selectedLecon != null) {
            try {
                // Charger la nouvelle scène à partir de votre fichier FXML cible
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatelecon.fxml"));
                Parent root = loader.load();

                // Obtenez le contrôleur de la vue de mise à jour et passez la leçon sélectionnée
                updatelecon controller = loader.getController();
                controller.setSelectedLecon(selectedLecon);

                // Obtenir la fenêtre (Stage) actuelle à partir de l'événement
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Remplacer la scène actuelle par la nouvelle scène
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                // Gérer les exceptions, par exemple en affichant un message d'erreur
                e.printStackTrace();
            }
        } else {
            afficherAlerteErreur("Aucune leçon sélectionnée", "Veuillez sélectionner une leçon à mettre à jour.");
        }
    }

    @FXML
    void delete(ActionEvent event) {
        lecon selectedLecon = tableView.getSelectionModel().getSelectedItem();
        if (selectedLecon != null) {
            try {
                rec.deleteC(selectedLecon.getId());
                tableView.getItems().remove(selectedLecon);  // Mettre à jour la TableView
                afficherAlerteInformation("Suppression réussie", "La leçon a été supprimée avec succès.");
                showNotification("Opération réussie", "La demande d'achat a été ajoutée avec succès.");
            } catch (SQLException e) {
                afficherAlerteErreur("Erreur", "Échec de la suppression de la leçon : " + e.getMessage());
            }
        } else {
            afficherAlerteErreur("Aucune sélection", "Veuillez sélectionner une leçon à supprimer.");
        }
    }


    private void afficherAlerteInformation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void afficheCategorie(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficheCategorie.fxml"));

            // Obtenir la fenêtre (Stage) actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Loading Error", "Could not load the view: " + e.getMessage());
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
    void rechercherLecon(KeyEvent keyEvent) {
        String searchText = chercherlecon.getText();
        if (!searchText.isEmpty()) {
            List<lecon> result = rec.chercherLecon(searchText);
            ObservableList<lecon> observableResult = FXCollections.observableList(result);
            tableView.setItems(observableResult);
        } else {
            // Si le champ de recherche est vide, affichez toutes les leçons
            initialize();
        }
    }


    public void triByprix(ActionEvent actionEvent) {
        try {
            List<lecon> personnes = rec.recuperer();
            personnes.sort(Comparator.comparingInt(lecon::getPrix));

            tableView.getItems().setAll(personnes);
        } catch (SQLException e) {
            e.printStackTrace();
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while sorting persons by age.");
            alert.showAndWait();
        }
    }

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }

    @FXML
    private void generatePDF() {
        lecon selectedlecon = tableView.getSelectionModel().getSelectedItem();

        if (selectedlecon != null) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Commencer un nouveau texte
                    contentStream.beginText();
                    contentStream.setNonStrokingColor(36, 0, 70);

                    // Ajouter du texte à votre document PDF en utilisant les données de l'élément sélectionné
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(100, 700);
                    contentStream.showText("Leçon sélectionnée : " + selectedlecon.getNom_lecon());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("URL : " + selectedlecon.getUrl());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Prix : " + selectedlecon.getPrix());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Description : " + selectedlecon.getDescription());
                    contentStream.endText(); // Terminer le texte

                    // Ajouter les leçons à votre PDF
                   /* int y = 680;
                    for (lecon lecon : tableView.getItems()) {
                        contentStream.beginText(); // Commencer un nouveau texte pour chaque élément de la table
                        contentStream.setFont(PDType1Font.HELVETICA, 10);
                        contentStream.newLineAtOffset(100, y);
                        contentStream.showText("Nom de la leçon : " + lecon.getNom_lecon());
                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText("URL : " + lecon.getUrl());
                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText("Prix : " + lecon.getPrix());
                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText("Description : " + lecon.getDescription());
                        contentStream.endText(); // Terminer le texte pour cet élément de la table
                        y -= 90;
                    }*/

//                    // Ajouter vos images dans les coins du document
//                    // Remplacez les URLs par vos propres URLs d'images
                    String currentDirectory = System.getProperty("user.dir");

                    // Print the current working directory
                    System.out.println("Current working directory: " +currentDirectory+ "/src/main/resources/img/yasuo.png");

                    PDImageXObject image1 = PDImageXObject.createFromFile(currentDirectory+"/src/main/resources/img/yasuo.png", document);
                    contentStream.drawImage(image1, 50, 750, 100, 100); // Coin supérieur gauche
//
                    PDImageXObject image2 = PDImageXObject.createFromFile(currentDirectory+"/src/main/resources/img/yasuo.png", document);
                    contentStream.drawImage(image2, 450, 750, 100, 100); // Coin supérieur droit

                    PDImageXObject image3 = PDImageXObject.createFromFile(currentDirectory+"/src/main/resources/img/yasuo.png", document);
                    contentStream.drawImage(image3, 50, 50, 100, 100); // Coin inférieur gauche

                    PDImageXObject image4 = PDImageXObject.createFromFile(currentDirectory+"/src/main/resources/img/yasuo.png", document);
                    contentStream.drawImage(image4, 450, 50, 100, 100); // Coin inférieur droit
                }

                // Sauvegardez le document PDF
                document.save("liste_lecons.pdf");
                document.close();

                showAlert("PDF généré", "Le PDF a été généré avec succès.");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la génération du PDF: " + e.getMessage());
            }}
}}


