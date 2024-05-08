package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.example.entities.Categorie;
import org.example.services.CategorieService;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;

public class afficheCategorie {

    public Label selectedNomCategorie;
    public Label selectedtype;
    public Button generatePDFButton;
    @FXML
    private TableView<Categorie> tableView1;

    @FXML
    private TableColumn<Categorie, String> nom_categorie;

    @FXML
    private TableColumn<Categorie, String> type;
    @FXML
    private TableColumn<Categorie, String> image;
    private String imageUrl;


    private final ImageView imageView = new ImageView();

    private final CategorieService rec = new CategorieService();
    @FXML
    private ImageView selectedImage;



    public void initialize() {
        nom_categorie.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        image.setCellValueFactory(new PropertyValueFactory<>("image")); // Utilise l'attribut image

        // Personnalisation de l'affichage de la colonne d'image
        image.setCellFactory(column -> {
            return new TableCell<Categorie, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        Image image = new Image(item); // Utilise directement l'URL de l'image
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setPreserveRatio(true);
                        setGraphic(imageView);
                    }
                }
            };
        });

        loadCategorieData();
    }



    @FXML
    void naviguerC(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterCategorie.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerCategorie(ActionEvent event) {
        Categorie selectedCategorie = tableView1.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateCategorie.fxml"));
                Parent root = loader.load();

                updateCategorie controller = loader.getController();
                controller.setSelectedCategorie(selectedCategorie);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Missing");
            alert.setContentText("Please select a Categorie to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Categorie selectedCategorie = tableView1.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            try {
                rec.deleteC(selectedCategorie.getId());
                tableView1.getItems().remove(selectedCategorie);
                showAlert("Suppression réussie", "La catégorie a été supprimée avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la suppression de la catégorie : " + e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner une catégorie à supprimer.");
        }
    }

        public void setSelectedImage(ImageView image) {
            this.selectedImage = image;
        }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadCategorieData() {
        List<Categorie> categorieList = rec.getAllCategories();
        ObservableList<Categorie> observableList = FXCollections.observableList(categorieList);
        tableView1.setItems(observableList);
    }

    public void naviguerLeçons(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherlecon.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");

            Stage currentStage = (Stage) tableView1.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void generatePDF() {
        Categorie selectedCategorie = tableView1.getSelectionModel().getSelectedItem();

        if (selectedCategorie != null) {
            try {
                // Récupérer l'URL de l'image à partir de la catégorie sélectionnée
                String imageURL = selectedCategorie.getImage();

                // Vérifier si l'URL de l'image est valide
                if (imageURL == null || imageURL.isEmpty()) {
                    showAlert("Erreur", "L'URL de l'image est vide.");
                    return;
                }

                // Création du document PDF
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Charger l'image depuis l'URL
                    PDImageXObject pdImage = PDImageXObject.createFromFile(imageURL, document);

                    // Dessiner l'image dans le PDF
                    contentStream.drawImage(pdImage, 50, 600); // Position de l'image

                    // Ajouter le titre et d'autres détails...
                }

                // Sauvegarder le document PDF
                String outputPath = "categorie_" + selectedCategorie.getId() + ".pdf";
                document.save(outputPath);
                document.close();

                showAlert("PDF généré", "Le PDF a été généré avec succès: " + outputPath);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la génération du PDF: " + e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner une catégorie.");
        }
    }

    public void refreshTableView() {
        loadCategorieData();
    }

    }




