package com.example.rankup.test;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;


public class Main {
    public static void main(String[] args) {
        Stripe.apiKey = "sk_test_51OqY7CHoZFcW2nLbru4vGp0qJAXPcedPd1rot93NGHHEZ7zpVmWCUO5BuxLU0wjLnQod4ozFotAnBdORWbMFFxmi004SLQPEJK";
        try {
            CustomerCreateParams params =
                    CustomerCreateParams.builder()
                            .setEmail("saidanimariem353@gmail.com")
                            .build();
            Customer customer = Customer.create(params);
            System.out.println("Customer created: " + customer);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}