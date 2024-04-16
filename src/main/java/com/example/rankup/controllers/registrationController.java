package com.example.rankup.controllers;


import com.example.rankup.entities.User;
import com.example.rankup.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.time.LocalDate;


public class registrationController {


    @FXML

 TextField usernamesignup;
    @FXML

    TextField emailsignup;
    @FXML
    PasswordField passwordsignup;
    @FXML
    DatePicker birthdatesignup;

    @FXML
    public void signup(ActionEvent actionEvent) {
        // Retrieve user input from the text fields
        String username = usernamesignup.getText();
        String email = emailsignup.getText();
        String password = passwordsignup.getText();
        // Assuming you have a method to get the selected date from the DatePicker
         LocalDate birthdate = birthdatesignup.getValue();

        // Perform validation checks on user input (optional)

        // Create a new User object with the provided information
        User newUser = new User(username, email, password, birthdate);
        // Call the registration service to register the new user
        boolean success = UserService.register(newUser);

        if (success) {
            // Registration successful, display a success message or navigate to another view
            System.out.println("User registration successful!");
        } else {
            // Registration failed, display an error message or handle it accordingly
            System.out.println("User registration failed!");
        }
    }




    }
