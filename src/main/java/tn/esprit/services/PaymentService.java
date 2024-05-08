package tn.esprit.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import tn.esprit.entities.Subscription_plan;

import java.util.HashMap;
import java.util.Map;

public class PaymentService {
    // Initialize Stripe with your API key
    static {
        Stripe.apiKey = "sk_test_51OqY7CHoZFcW2nLbru4vGp0qJAXPcedPd1rot93NGHHEZ7zpVmWCUO5BuxLU0wjLnQod4ozFotAnBdORWbMFFxmi004SLQPEJK";
    }

    // Method to create a Payment Intent
    public PaymentIntent createPaymentIntent(Subscription_plan plan, String customerId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", plan.getPrix() * 100); // Amount in cents
        params.put("currency", "usd");
        params.put("customer", customerId); // ID of the customer making the payment
        // Add more parameters as needed

        return PaymentIntent.create(params);
    }
}
