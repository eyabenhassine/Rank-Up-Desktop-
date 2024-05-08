package org.example.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import org.example.entities.lecon;
import org.example.services.leconService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.sql.SQLException;


public class Ajouterlecon  {

    @FXML
    private TextField descriptionFT;

    @FXML
    private TextField nomFT;

    @FXML
    private TextField prixT;

    @FXML
    private TextField urlFT;
    private final leconService lc = new leconService();
    @FXML
    void ajouterL(ActionEvent event) {
        try {
            String description = descriptionFT.getText();
            String nom = nomFT.getText();
            String prixStr = prixT.getText();
            String url = urlFT.getText();

            // Vérifier si tous les champs sont remplis
            if (description.isEmpty() || nom.isEmpty() || prixStr.isEmpty() || url.isEmpty()) {
                // Afficher une alerte si un champ est vide
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }

            // Vérifier si le prix est un entier valide et > 0
            int prix;
            try {
                prix = Integer.parseInt(prixStr);
                if (prix <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                // Afficher une alerte si le prix n'est pas un entier valide ou <= 0
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setContentText("Le prix doit être un nombre entier strictement positif.");
                alert.showAndWait();
                return;
            }


            // Vérifier si le nom de la leçon ne dépasse pas 8 caractères
            if (nom.length() > 8) {
                // Afficher une alerte si le nom de la leçon dépasse 8 caractères
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setContentText("Le nom de la leçon ne doit pas dépasser 8 caractères.");
                alert.showAndWait();
                return;
            }

            // Vérifier si l'URL commence par "http://"
            if (!url.startsWith("http://")) {
                // Afficher une alerte si l'URL ne commence pas par "http://"
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur");
                alert.setContentText("L'URL doit commencer par 'http://'.");
                alert.showAndWait();
                return;
            }

            // Si toutes les vérifications sont passées, ajouter la leçon
            lc.add(new lecon(description, nom, prix, url));
            showNotification("Opération réussie", "La demande d'achat a été ajoutée avec succès.");
        } catch (SQLException e) {
            // Afficher une alerte en cas d'erreur SQL
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
         final String ACCOUNT_SID = "AC4fccfdaa904e35573b74219546d5707d";
        final String AUTH_TOKEN = "1637d2641766535cf60f04293417da60";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

// Définissez le numéro de téléphone Twilio à partir duquel vous souhaitez envoyer le SMS
        String twilioPhoneNumber = "+12073053174"; // Remplacez par votre numéro Twilio

// Définissez le numéro de téléphone destinataire
        String recipientPhoneNumber = "+21656757921"; // Remplacez par le numéro de téléphone du destinataire

// Construisez le message à envoyer
        String smsMessage = "La leçon '" + "' a été ajoutée avec succès.";

// Envoyez le message
        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        smsMessage)
                .create();

// Affichez l'identifiant du message en cas de succès
        System.out.println("Message sent successfully. Message SID: " + message.getSid());


}

    public void naviguer(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherlecon.fxml"));
            nomFT.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle() // Utilisez le style sombre par défaut
                .graphic(null) // Supprimez l'icône par défaut
                .hideAfter(javafx.util.Duration.seconds(5)) // Masquez la notification après 5 secondes
                // .position(Pos.TOP_RIGHT) // Positionnez la notification en haut à droite
                .onAction(event -> {
                    // Code à exécuter lorsque la notification est cliquée
                    System.out.println("Notification cliquée");
                })
                .show();
    }
}
