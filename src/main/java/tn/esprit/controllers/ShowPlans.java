package tn.esprit.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.entities.Subscription_plan;
import tn.esprit.services.Subscription_PlanService;
import tn.esprit.services.PaymentService;
import com.stripe.Stripe;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowPlans {
    @FXML
    private GridPane subscriptionPlanGrid;

    private final Subscription_PlanService subscriptionPlanService = new Subscription_PlanService();
    private final PaymentService paymentService = new PaymentService();

    public ShowPlans() throws IOException {
    }

    // Méthode pour charger les données de plan d'abonnement dans les labels appropriés
    public void initialize() {
        // Supposons que vous avez une liste de plans d'abonnement
        List<Subscription_plan> subscriptionPlans = subscriptionPlanService.getAll(); // Obtenir vos plans d'abonnement depuis quelque part

        // Boucle à travers la liste et afficher chaque plan d'abonnement dans une ligne de la grille
        int rowIndex = 1; // Commencez à partir de la deuxième ligne après le libellé de l'en-tête
        for (Subscription_plan plan : subscriptionPlans) {
            Label planIdLabel = new Label(Integer.toString(plan.getId()));
            Label planTypeLabel = new Label(plan.getType());
            Label planPriceLabel = new Label(Float.toString(plan.getPrix()));
            Label planAdditionalInfoLabel = new Label(plan.getAdditional_info());
            Button paymentButton = new Button("Get");
            paymentButton.setOnAction(e -> initiatePayment(plan)); // Passer le plan d'abonnement à la méthode showPlanDetails

            subscriptionPlanGrid.addRow(rowIndex++, planIdLabel, planTypeLabel, planPriceLabel, planAdditionalInfoLabel, paymentButton);
        }
    }

    // Méthode pour initier le processus de paiement pour un plan d'abonnement sélectionné
    private void initiatePayment(Subscription_plan plan) {
        // Récupérer les informations du plan d'abonnement (par exemple : ID, prix)
        String planId = Integer.toString(plan.getId());
        float planPrice = plan.getPrix();

        // Initialisez Stripe (s'il n'est pas déjà initialisé)
        initializeStripe();

        // Créer un paiement via Stripe
        createPaymentIntent(planId, planPrice);
    }
    // Initialisez Stripe (remplacez ceci avec votre initialisation Stripe réelle)
    private void initializeStripe() {
        Stripe.apiKey = "sk_test_51OqY7CHoZFcW2nLbru4vGp0qJAXPcedPd1rot93NGHHEZ7zpVmWCUO5BuxLU0wjLnQod4ozFotAnBdORWbMFFxmi004SLQPEJK";
    }

    // Créer un paiement Intent via Stripe pour le plan sélectionné
    // Créer un paiement Intent via Stripe pour le plan sélectionné
    private void createPaymentIntent(String planId, float planPrice) {
        // Use Stripe's Java library to create a payment intent
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int) (planPrice * 100)); // Amount is in cents
            params.put("currency", "usd"); // Currency is USD for example, change as needed
            params.put("description", "Payment for subscription plan: " + planId);

            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount((long) (planPrice * 100))
                    .setCurrency("usd")
                    .setDescription("Payment for subscription plan: " + planId)
                    .build();

            PaymentIntent intent = PaymentIntent.create(createParams);

            // Now you can use 'intent' object to handle the payment flow
            // For example, you can get the client secret and pass it to the client-side for payment confirmation
            String clientSecret = intent.getClientSecret();
            // Pass the 'clientSecret' to your frontend to complete the payment process

            System.out.println("Payment intent created: " + intent.toJson());
            // Inform the client that the payment was successful
            showPaymentSuccessMessage();
        } catch (StripeException e) {
            // Handle any errors that might occur during payment intent creation
            e.printStackTrace();
        }
    }

    // Declare a property to hold the client secret
    private final StringProperty clientSecretProperty = new SimpleStringProperty();

    // Getter method for the client secret property
    public String getClientSecret() {
        return clientSecretProperty.get();
    }

    // Function to update the client secret property
    private void updateClientSecret(String clientSecret) {
        clientSecretProperty.set(clientSecret);
    }

    private void showPaymentSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Success");
        alert.setHeaderText(null);
        alert.setContentText("Your payment was successful!");

        // Show the alert dialog
        alert.showAndWait();
    }

    // Méthode pour afficher les détails d'un plan d'abonnement
    private void showPlanDetails(Subscription_plan plan) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowDetails.fxml"));
            Parent root = loader.load();
            PlanDetails controller = loader.getController();
            controller.initialize(plan);
            Stage currentStage = (Stage) subscriptionPlanGrid.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPlanDetails(ActionEvent event) {
    }

    public void BackToHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
