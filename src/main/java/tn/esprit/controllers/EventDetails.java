package tn.esprit.controllers;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import tn.esprit.entities.Event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventDetails {
    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventStartDateLabel;

    @FXML
    private Label eventEndDateLabel;

    @FXML
    private Label eventTypeLabel;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private ImageView qrCodeImageView;

    public void initialize(Event event) {
        eventNameLabel.setText(event.getNom_event());
        eventStartDateLabel.setText("Start Date: " + event.getDate_debut());
        eventEndDateLabel.setText("End Date: " + event.getDate_fin());
        eventTypeLabel.setText("Type: " + event.getType());
        eventDescriptionLabel.setText("Description: " + event.getDescription());

        // Generate QR code for event details
        generateQRCodeForEvent(event);
    }

    private void generateQRCodeForEvent(Event event) {
        // Concatenate event details into a single string
        String eventDetails = "Event Name: " + event.getNom_event() + "\n"
                + "Start Date: " + event.getDate_debut() + "\n"
                + "End Date: " + event.getDate_fin() + "\n"
                + "Type: " + event.getType() + "\n"
                + "Description: " + event.getDescription();



        // Set the width and height of the QR code
        int width = 150;
        int height = 150;

        // Generate the QR code image based on the event details
        Image qrImage = generateQRCodeImage(eventDetails, width, height);

        // Set the generated QR code image to the ImageView
        qrCodeImageView.setImage(qrImage);
    }

    private Image generateQRCodeImage(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 1);

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}