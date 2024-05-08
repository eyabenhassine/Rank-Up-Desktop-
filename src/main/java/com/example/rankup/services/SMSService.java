package tn.esprit.services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {
    // Vos identifiants Twilio
    private static final String ACCOUNT_SID = "ACc9c627e21ad6900d0432e0f3eeddff1c";
    private static final String AUTH_TOKEN = "a0c2c5cad27d106577c065e112a7a8c3";
    private static final String FROM_NUMBER = "+14052469879"; // Votre numéro Twilio ici

    public static void envoyerSMS(String numeroDestinataire, String message) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message.creator(new PhoneNumber(numeroDestinataire), new PhoneNumber(FROM_NUMBER), message).create();
            System.out.println("SMS envoyé avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi du SMS: " + e.getMessage());
        }
    }
}
