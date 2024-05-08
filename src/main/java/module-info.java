module com.example.rankup {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires jbcrypt;
    requires mail;
    requires twilio;
    requires org.controlsfx.controls;
    requires org.apache.pdfbox;
    opens com.example.rankup to javafx.fxml;
    exports com.example.rankup;
    opens com.example.rankup.entities;
    exports com.example.rankup.controllers;
    opens com.example.rankup.controllers to javafx.fxml;
}