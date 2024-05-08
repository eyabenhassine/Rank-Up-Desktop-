package tn.esprit.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import tn.esprit.entities.Subscription_plan;
import tn.esprit.services.PaymentService;

public class PaymentController {
    private PaymentService paymentService;

    public PaymentController() {
        this.paymentService = new PaymentService();
    }

    public void processPayment(Subscription_plan plan, String customerId) {
        try {
            // Create a Payment Intent for the selected subscription plan
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(plan, customerId);

            // Handle the payment intent, e.g., redirecting the user to a payment page
            // or displaying payment details for confirmation
        } catch (StripeException e) {
            // Handle Stripe exceptions
            e.printStackTrace();
        }
    }
}