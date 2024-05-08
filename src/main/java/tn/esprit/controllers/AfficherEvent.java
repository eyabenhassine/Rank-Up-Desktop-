package tn.esprit.controllers;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.layout.element.Image;

import com.itextpdf.layout.property.TextAlignment;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.ChoiceBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.itextpdf.layout.element.Cell;

import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class AfficherEvent {
    @FXML
    private TableColumn<Event, String> DescriptionCol;

    @FXML
    private TableColumn<Event, String>  EndDateCol;

    @FXML
    private TableColumn<Event, String>  NomECol;

    @FXML
    private TableColumn<Event, String>  StartDateCol;

    @FXML
    private TableColumn<Event, String>  TypeCol;

    @FXML
    private TableView<Event> tableView;

    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> colonneChoiceBox;

    @FXML
    private ChoiceBox<String> ordreChoiceBox;



    private final EventService rec;

    {
        try {
            rec = new EventService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        try {
            // Récupérer la liste des événements à partir de la méthode afficherEvents()
            List<Event> events = rec.afficherEvents();
            System.out.println("Event loaded: " + events);

            // Créer une ObservableList à partir de la liste d'événements
            ObservableList<Event> observableList = FXCollections.observableList(events);

            // Définir les éléments de la TableView avec la liste observable
            tableView.setItems(observableList);

            // Configuration des PropertyValueFactory pour chaque colonne de la TableView
            NomECol.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
            StartDateCol.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            EndDateCol.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

            // Actualiser la TableView pour refléter les changements
            tableView.refresh();
            // Ajouter un écouteur de changement au champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                search(newValue.trim()); // Appeler la méthode search avec le nouveau texte
            });


        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Échec de l'initialisation de la TableView : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void deleteEvent(ActionEvent actionEvent) {
        Event selectedEvent = tableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                rec.deleteEvent(selectedEvent.getId());
                // Appeler la méthode deleteEvent() pour supprimer l'événement sélectionné
                //deleteEvent(selectedEvent.getId());
                // Mettre à jour la TableView en supprimant l'événement sélectionné de la liste des éléments
                tableView.getItems().remove(selectedEvent);
                showAlert("Suppression réussie", "L'événement a été supprimé avec succès.");
                showNotification("Opération réussie", "Evenement a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la suppression de l'événement : " + e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un événement à supprimer.");
        }

    }
    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterEvent.fxml")));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierEvent(ActionEvent event) {
        Event selectedEvent = tableView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
                Parent root = loader.load();

                ModifierEvent controller = loader.getController();
                controller.setSelectedEvent(selectedEvent);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Aucun événement sélectionné.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sélection manquante");
            alert.setContentText("Veuillez sélectionner un événement à modifier.");
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


    public void naviguerAdd(ActionEvent actionEvent) {
        // Implémentez la navigation vers l'ajout si nécessaire
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterEvent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherAlerteErreur("Erreur", "Impossible de charger la vue d'ajout d'événement.");
        }
    }
    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    private void search(String searchText) {
        final String finalSearchText = searchText.toLowerCase().trim();

        if (!finalSearchText.isEmpty()) {
            try {
                List<Event> searchResults = rec.chercher(finalSearchText);
                ObservableList<Event> observableList = FXCollections.observableArrayList(searchResults);

                FilteredList<Event> filteredList = new FilteredList<>(observableList, p -> true);

                filteredList.setPredicate(event -> {
                    String lowerCaseNom = event.getNom_event().toLowerCase();
                    String lowerCaseDD = event.getDate_debut().toLowerCase();
                    String lowerCaseDF = event.getDate_fin().toLowerCase();
                    String lowerCaseType = event.getType().toString().toLowerCase();
                    String lowerCaseDescription = event.getDescription().toLowerCase();


                    return lowerCaseNom.contains(finalSearchText)
                            || lowerCaseDD.contains(finalSearchText)
                            || lowerCaseDF.contains(finalSearchText)
                            || lowerCaseType.contains(finalSearchText)
                            || lowerCaseDescription.contains(finalSearchText);
                            
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

    private void loadReclamationData() {
        List<Event> reclamations = rec.afficherEvents();
        ObservableList<Event> observableList = FXCollections.observableList(reclamations);
        tableView.setItems(observableList);
    }


    /*public void search(ActionEvent actionEvent) {
        String searchText = searchField.getText().trim();

        if (!searchText.isEmpty()) {
            List<Event> searchResults = null;
            try {
                searchResults = rec.chercher(searchText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Convert the list to observable list
            ObservableList<Event> observableList = FXCollections.observableArrayList(searchResults);

            // Define the cell value factories for each column
            NomECol.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
            StartDateCol.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            EndDateCol.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));



            // Set the items to the table
            tableView.setItems(observableList);

            // Create a sorted list to sort the table based on the selected column and order
            SortedList<Event> sortedList = new SortedList<>(observableList);

            // Set the comparator based on the selected column and order
            Comparator<Event> comparator = getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox);
            sortedList.setComparator(comparator);

            // Bind the sorted list to the table
            tableView.setItems(sortedList);
        } else {
            showAlert("Input Error", "Please enter a search term.");
        }

    }*/

    private Comparator<Event> getComparatorFromChoiceBox(ChoiceBox<String> colonneChoiceBox, ChoiceBox<String> ordreChoiceBox) {
        String selectedColumn = colonneChoiceBox.getValue();
        String selectedOrder = ordreChoiceBox.getValue();

        Comparator<Event> comparator = null;

        switch (selectedColumn) {
            case "Nom":
                comparator = Comparator.comparing(Event::getNom_event);
                break;
            case "Starting Day":
                comparator = Comparator.comparing(Event::getDate_debut);
                break;
            case "End Day":
                comparator = Comparator.comparing(Event::getDate_fin);
                break;
            case "Type":
                comparator = Comparator.comparing(Event::getType);
                break;
            case "Description":
                comparator = Comparator.comparing(Event::getDescription);
                break;

            default:
                break;
        }

        if (comparator != null && selectedOrder != null && selectedOrder.equals("DESC")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    @FXML
    void trier(ActionEvent event) {
        tableView.getItems().sort(getComparatorFromChoiceBox(colonneChoiceBox, ordreChoiceBox));
    }

    private void displaySelectedEventDetails(Event event) {
        // Afficher les détails de la réclamation sélectionnée
        System.out.println("Selected Event: " + event);

        // Vous pouvez afficher ces détails où vous le souhaitez
        // Par exemple, affichez-les dans des labels à côté de la table
        NomECol.setText(event.getNom_event());
        StartDateCol.setText(String.valueOf(event.getDate_debut()));
        EndDateCol.setText(event.getDate_fin());
        TypeCol.setText(event.getType());
        DescriptionCol.setText(event.getDescription());
    }




    @FXML
    void GenererPdfEvent(ActionEvent event) {
        // Sélectionner l'emplacement et le nom du fichier PDF
        File file = choisirFichierPDF();

        // Générer le PDF si un fichier est sélectionné
        if (file != null) {
            try {
                // Créer le document PDF
                try (PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                     PdfDocument pdf = new PdfDocument(writer);
                     Document document = new Document(pdf)) {

                    // Ajouter un titre au document
                    ajouterTitre(document);

                    // Créer et ajouter la table d'événements
                    creerEtAjouterTable(document);
                }

                // Afficher un message de succès
                System.out.println("PDF des événements généré avec succès !");
            } catch (IOException e) {
                // Gérer les erreurs d'entrée/sortie
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void generatePDF() {
        Event selectedEvent = tableView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            // Créer un sélecteur de fichiers
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");

            // Définir l'extension par défaut et le nom du fichier
            fileChooser.setInitialFileName("Event_" + selectedEvent.getId() + ".pdf");
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
                        PDImageXObject logo = PDImageXObject.createFromFile("src/logo0.png", document);
                        //Redimensionner le logo
                        float logoWidth = logo.getWidth() / 4; // Largeur du logo divisée par 4
                        float logoHeight = logo.getHeight() / 4; // Hauteur du logo divisée par 4
                        contentStream.drawImage(logo, 50, 750 - logoHeight, logoWidth, logoHeight);

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
                        contentStream.showText("Nom: " + selectedEvent.getNom_event());
                        contentStream.newLineAtOffset(0, -20); // Décaler verticalement pour la prochaine ligne
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Date de debut: " + selectedEvent.getDate_debut());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Date: " + selectedEvent.getDate_fin());
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Type: " + selectedEvent.getType());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("------------------------------------------");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Description: " + selectedEvent.getDescription());
                        contentStream.newLineAtOffset(0, -20);

                        // Fin du texte
                        contentStream.endText();
                    }

                    // Sauvegarder le document PDF
                    document.save(file);
                    document.close();

                    showAlert("PDF généré", "Le PDF a été généré avec succès: " + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Erreur lors de la génération du PDF: " + e.getMessage());
                }
            }
        } else {
            showAlert("Sélection manquante", "Veuillez sélectionner un événement pour générer un PDF.");
        }
    }


    // Méthode pour sélectionner l'emplacement et le nom du fichier PDF
    private File choisirFichierPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF (*.pdf)", "*.pdf"));
        return fileChooser.showSaveDialog(null);
    }

    // Méthode pour ajouter un titre au document PDF
    private void ajouterTitre(Document document) {
        Paragraph titre = new Paragraph("Liste des événements")
                //.setFontColor(Color.BLACK)
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(titre);
    }

    // Méthode pour créer et ajouter la table d'événements
    private void creerEtAjouterTable(Document document) {
        // Créer une table avec 5 colonnes
        Table table = new Table(5);
        table.setWidth(100);

        // Ajouter une en-tête de table avec un style personnalisé
        ajouterEnTeteTable(table);

        // Récupérer la liste des événements depuis la TableView
        ObservableList<Event> observableList = tableView.getItems();

        // Ajouter les données des événements à la table
        for (Event ev : observableList) {
            table.addCell(ev.getNom_event());
            table.addCell(ev.getDate_debut());
            table.addCell(ev.getDate_fin());
            table.addCell(ev.getType());
            table.addCell(ev.getDescription());
        }

        // Ajouter la table au document
        document.add(table);
    }

    // Méthode pour ajouter une en-tête de table avec un style personnalisé
    private void ajouterEnTeteTable(Table table) {
        table.addHeaderCell("Nom");
        table.addHeaderCell("Date de début");
        table.addHeaderCell("Date de fin");
        table.addHeaderCell("Type");
        table.addHeaderCell("Description");

        table.getHeader().setBackgroundColor(new DeviceGray(0.75f));
    }



    /*private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle()
                .graphic(new Image(getClass().getResourceAsStream("/path/to/icon.png"))) // Ajouter une icône personnalisée
                .backgroundColor(Color.web("#ffcc00")) // Changer la couleur de fond
                .textColor(Color.BLACK) // Changer la couleur du texte
                .font(Font.font("Arial", FontWeight.BOLD, 14)) // Changer la taille et le style de la police
                .show();
    }*/

    @FXML

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Vous pouvez personnaliser le style ici
                .show();
    }


    public void handleSearch(javafx.scene.input.KeyEvent keyEvent) {
        String searchText = searchField.getText().trim();
        search(searchText);
    }
}