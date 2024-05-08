package tn.esprit.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ReclamationService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Alert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AfficherReclamation {

    @FXML
    private TableColumn<Reclamation, Integer> NumtelCol;

    @FXML
    private TableColumn<Reclamation, String> dateCol;

    @FXML
    private TableColumn<Reclamation, String> descriptionCol;

    @FXML
    private TableColumn<Reclamation, String> nomcol;

    @FXML
    private TableView<Reclamation> tableView;

    @FXML
    private TableColumn<Reclamation, String> typeCol;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> colonneChoiceBox;

    @FXML
    private ChoiceBox<String> ordreChoiceBox;
    @FXML
    private Label selectedNomLabel;

    @FXML
    private Label selectedNumTelLabel;

    @FXML
    private Label selectedTypeLabel;

    @FXML
    private Label selectedDescriptionLabel;

    @FXML
    private Label selectedDateLabel;

    private final ReclamationService rec = new ReclamationService();

    @FXML
    void initialize() {
        try {
            List<Reclamation> reclamations = rec.afficherRec();
            System.out.println("Reclamations loaded: " + reclamations);

            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            tableView.setItems(observableList);

            nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            NumtelCol.setCellValueFactory(new PropertyValueFactory<>("NumTel"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

            tableView.refresh();

            // Ajouter un gestionnaire d'événements pour la sélection de la table
            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Afficher les détails de la réclamation sélectionnée
                    displaySelectedReclamationDetails(newSelection);
                }
            });
            // Ajouter un écouteur de changement au champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                search(newValue.trim()); // Appeler la méthode search avec le nouveau texte
            });

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to initialize the TableView: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void displaySelectedReclamationDetails(Reclamation reclamation) {
        // Afficher les détails de la réclamation sélectionnée
        System.out.println("Selected Reclamation: " + reclamation);

        // Vous pouvez afficher ces détails où vous le souhaitez
        // Par exemple, affichez-les dans des labels à côté de la table
        selectedNomLabel.setText(reclamation.getNom());
        selectedNumTelLabel.setText(String.valueOf(reclamation.getNumTel()));
        selectedTypeLabel.setText(reclamation.getType());
        selectedDescriptionLabel.setText(reclamation.getDescription());
        selectedDateLabel.setText(reclamation.getDate());
    }









    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterReclamation.fxml")));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierReclamation(ActionEvent event) {
        Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateReclamation.fxml"));
                Parent root = loader.load();

                UpdateReclamation controller = loader.getController();
                controller.setSelectedReclamation(selectedReclamation);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No reclamation selected.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Missing");
            alert.setContentText("Please select a reclamation to modify.");
            alert.showAndWait();
        }
    }

    public void deleteReclamation(ActionEvent actionEvent) {
        Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                rec.deleteReclamation(selectedReclamation.getId());
                tableView.getItems().remove(selectedReclamation);  // Update the table view
                showAlert("Deletion Successful", "The reclamation has been deleted successfully.");
                showNotification("Opération réussie", "Evenement a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete the reclamation: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a reclamation to delete.");
        }
    }




    @FXML
    private void handleSearch(KeyEvent event) {
        String searchText = searchField.getText().trim();
        search(searchText);
    }

    private void search(String searchText) {
        final String finalSearchText = searchText.toLowerCase().trim();

        if (!finalSearchText.isEmpty()) {
            try {
                List<Reclamation> searchResults = rec.chercher(finalSearchText);
                ObservableList<Reclamation> observableList = FXCollections.observableArrayList(searchResults);

                FilteredList<Reclamation> filteredList = new FilteredList<>(observableList, p -> true);

                filteredList.setPredicate(reclamation -> {
                    String lowerCaseNom = reclamation.getNom().toLowerCase();
                    String lowerCaseType = reclamation.getType().toLowerCase();
                    String lowerCaseDescription = reclamation.getDescription().toLowerCase();
                    String lowerCaseDate = reclamation.getDate().toString().toLowerCase();

                    return lowerCaseNom.contains(finalSearchText)
                            || lowerCaseType.contains(finalSearchText)
                            || lowerCaseDescription.contains(finalSearchText)
                            || lowerCaseDate.contains(finalSearchText);
                });

                tableView.setItems(filteredList);
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les exceptions...
            }
        } else {
            loadReclamationData();
        }
    }
    @FXML
    void trier(ActionEvent event) {
        tableView.getItems().sort(getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox));
    }

    private Comparator<Reclamation> getComparatorFromChoiceBox(ChoiceBox<String> colonneChoiceBox, ChoiceBox<String> ordreChoiceBox) {
        String selectedColumn = colonneChoiceBox.getValue();
        String selectedOrder = ordreChoiceBox.getValue();

        Comparator<Reclamation> comparator = null;

        switch (selectedColumn) {
            case "Nom":
                comparator = Comparator.comparing(Reclamation::getNom);
                break;
            case "NumTel":
                comparator = Comparator.comparingInt(Reclamation::getNumTel);
                break;
            case "Type":
                comparator = Comparator.comparing(Reclamation::getType);
                break;
            case "Description":
                comparator = Comparator.comparing(Reclamation::getDescription);
                break;
            case "Date":
                comparator = Comparator.comparing(Reclamation::getDate);
                break;
            default:
                break;
        }

        if (comparator != null && selectedOrder != null && selectedOrder.equals("DESC")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }



    private void loadReclamationData() {
        List<Reclamation> reclamations = rec.afficherRec();
        ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
        tableView.setItems(observableList);
    }
    @FXML
    void trierReclamations(ActionEvent event) {
        String colonne = colonneChoiceBox.getValue();
        String ordre = ordreChoiceBox.getValue();

        try {
            List<Reclamation> reclamationsTrie = rec.trier(colonne, ordre);
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamationsTrie);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            // Gérer les exceptions...
        }
    }
    @FXML
    private void generatePDF() {
        Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            // Créer un sélecteur de fichiers
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");

            // Définir l'extension par défaut et le nom du fichier
            fileChooser.setInitialFileName("Reclamation_" + selectedReclamation.getId() + ".pdf");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            // Afficher la boîte de dialogue pour choisir l'emplacement et le nom du fichier PDF
            Stage stage = (Stage) tableView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    // Création du document PDF
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    document.addPage(page);

                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        // Charger le logo
                        PDImageXObject logo = PDImageXObject.createFromFile("src/logo2.png", document);
                        PDImageXObject logo1 = PDImageXObject.createFromFile("src/left.png", document);
                        PDImageXObject logo2 = PDImageXObject.createFromFile("src/right.png", document);
                        float logoWidth = logo.getWidth() / 6; // Réduire encore la taille du logo
                        float logoHeight = logo.getHeight() / 6; // Réduire encore la taille du logo

                        // Affichage du logo en haut à gauche
                        contentStream.drawImage(logo, 50, 750 - logoHeight, logoWidth, logoHeight);

                        // Affichage du logo en haut à droite
                        contentStream.drawImage(logo, page.getMediaBox().getWidth() - 50 - logoWidth, 750 - logoHeight, logoWidth, logoHeight);
                        // Affichage du logo en bas à gauche
                        contentStream.drawImage(logo1, 50, 50, logoWidth, logoHeight);

                        // Affichage du logo en bas à droite
                        contentStream.drawImage(logo2, page.getMediaBox().getWidth() - 50 - logoWidth, 50, logoWidth, logoHeight);



                        // Ajouter le titre
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(200, 750); // Position du titre
                        contentStream.showText("Détails de la réclamation");
                        contentStream.endText();

                        // Début du texte des détails de réclamation
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(100, 650); // Position verticale initiale

                        // Afficher les détails de la réclamation avec des séparateurs
                        contentStream.showText("Nom: " + selectedReclamation.getNom());
                        contentStream.newLineAtOffset(0, -20); // Décaler verticalement pour la prochaine ligne
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("NumTel: " + selectedReclamation.getNumTel());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Type: " + selectedReclamation.getType());
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Description: " + selectedReclamation.getDescription());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Date: " + selectedReclamation.getDate());
                        contentStream.newLineAtOffset(0, -20);

                        // Fin du texte
                        contentStream.endText();
                    }

                    // Sauvegarder le document PDF
                    document.save(file);
                    document.close();

                    showAlert("PDF généré", "Le PDF a été généré avec succès: " + file.getAbsolutePath());
                    showNotification("PDF réussie", " PDFF générer avec succès.");

                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Erreur lors de la génération du PDF: " + e.getMessage());
                }
            }
        } else {
            showAlert("Sélection manquante", "Veuillez sélectionner un événement pour générer un PDF.");
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

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }

}