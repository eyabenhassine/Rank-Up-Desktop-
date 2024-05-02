package com.example.rankup.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "ACa646db80db14487da6f32c12ad2cf361";
    public static final String AUTH_TOKEN = "cdf3ef0713c20f91ae73f2beadd6e702";
    public static final String TWILIO_PHONE_NUMBER = "+14026955227";


    public static void sendSMS(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();
    }
}