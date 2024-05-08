package com.example.rankup.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "ACaf2ebced6cc47cb1c63a40515fba8c2a";
    public static final String AUTH_TOKEN = "6756c878dd1a611c7d33872f5a8185ec";
    public static final String TWILIO_PHONE_NUMBER = "+15597084928";


    public static void sendSMS(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();
    }
}