package com.example.rankup.test;

import com.example.rankup.entities.Reclamation;
import com.example.rankup.services.ReclamationService;
import com.example.rankup.services.SuiviReclamationService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        SuiviReclamationService suiviReclamationService = new SuiviReclamationService();
        ReclamationService sr = new ReclamationService();
        List<Reclamation> malist = new ArrayList<>(sr.show());
        for (Reclamation r : malist){
            System.out.println(r);
        }

 /*  int id = 10;
       SuiviReclamation sr = suiviReclamationService.getOne(10);
        System.out.println(sr);
    /*   List<SuiviReclamation> malist = new ArrayList<>(suiviReclamationService.show());
        for (SuiviReclamation sr : malist){
            System.out.println(sr);
        }
  /*ReclamationService rec = new ReclamationService();
        // Define the date string
        String dateString = "2024-04-14";

        // Parse the date string to LocalDate
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return; // Exit the program if the date is invalid
        }

        // Now, you can pass 'date' to the Reclamation constructor
        Reclamation reclamation = new Reclamation("eyouta", 123456378, "coach", "testestest", date);

        try {
            rec.add(reclamation);
            System.out.println("Reclamation added successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }*/
 ///////// update ////////
        // Step 1: Create an instance of ReclamationService
        /* ReclamationService recService = new ReclamationService();

        // Step 2: Create an instance of Reclamation
        // Define the ID of the reclamation you want to update
        int reclamationId = 4; // You must provide the existing Reclamation ID

        // Define the updated data for the reclamation
        String updatedNom = "eyaaaa ayouta";
        int updatedNumTel = 98765432;
        String updatedType = "coachs";
        String updatedDescription = "test update yaaa rabbiii yenja7 hal update";
        LocalDate updatedDate = LocalDate.parse("2024-08-15");

        // Create a new Reclamation instance with the existing ID and updated data
        Reclamation reclamationToUpdate = new Reclamation(reclamationId, updatedNom, updatedNumTel, updatedType, updatedDescription, updatedDate);

        // Step 3: Call the update method with the Reclamation instance
        try {
            recService.update(reclamationToUpdate);
            System.out.println("Reclamation updated successfully.");
        } catch (SQLException e) {
            System.err.println("Database error while updating reclamation: " + e.getMessage());
        }*/



////////////////// delete/////////////


        // Step 1: Create an instance of ReclamationService
      /*  ReclamationService recService = new ReclamationService();

        // Step 2: Specify the id of the reclamation you want to delete
        int reclamationIdToDelete = 1; // Specify the id of the reclamation you want to delete

        // Step 3: Call the delete method with the id
        try {
            recService.delete(reclamationIdToDelete);
            System.out.println("Reclamation deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Database error while deleting reclamation: " + e.getMessage());
        }*/
































    }















    }
