package com.example.rankup;

import com.example.rankup.entities.Event;
import com.example.rankup.entities.Sponsor;
import com.example.rankup.services.EventService;
import com.example.rankup.services.SponsorService;
import com.example.rankup.util.MaConnexion;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {

        //--------------------------------------------------EVENT--------------------------------------------------

        //MaConnexion mac = MaConnexion.getInstance();
        //EventService es = new EventService();

        //----------ADD---------------
        //Event event = new Event("Mahmoud","2025-01-01","2025-01-03","Display", "List");
        //es.add(event);
        //es.insert(event);


        //---------DISPLAY---------------
        //System.out.println(es.getAll());





        //-----------UPDATE-----------------
       /* // Step 1: Create an instance of ReclamationService
        EventService rs = new EventService();

        // Step 2: Create an instance of ReclamationService
        //Define the ID of the event you want to update
        int EventId = 21; // You must provide the existing Event ID


        // Define the updated data for the reclamation
        String UpdateNom = "new java";
        String UpdateDateD = LocalDate.parse("2026-03-03").toString();
        String UpdateDateF = LocalDate.parse("2028-06-09").toString();
        String UpdateType = "update";
        String UpdateDescription = "last las last try for the update";
        System.out.println("UpdatedDateD:"+UpdateDateD);
        System.out.println("UpdatedDateF:"+UpdateDateF);
        // Create a new Event instance with the existing ID and update Data
        Event eventUpdate = new Event(EventId,UpdateNom,UpdateDateD,UpdateDateF,UpdateType,UpdateDescription);

        // Step 3: Call the update method with the Reclamation instance
        try {
            es.update(eventUpdate);
            System.out.println("Reclamation updated Successfully.");
        } catch (SQLException e){
          System.err.println("Database error while updating reclamation: " + e.getMessage());
        }*/


        //--------DELETE-----------

        //Step 1 : Create an instance of EventService
        //Deja creer

        // Step 2: Specify the id of the reclamation you want to delete
        /*int eventIdToDelete = 22;

        //Step 3 : Call the delete method with the id
        try {
            es.delete(eventIdToDelete);
            System.out.println("Reclamation deleted successfully.");
        }catch (SQLException e){
            System.out.println("Database error while deleting reclamation" + e.getMessage());
        }*/


        //--------------------------------------------------SPONSOR--------------------------------------------------
        //SponsorService sp = new SponsorService();

        //--------ADD----------
        /*Sponsor sponsor = new Sponsor("ESPRIT","ESPRIT_GHAZELA","ESPRIT@ESPRIT.TN");
        sp.add(sponsor);*/


        //--------UPDATE----------
         // Step 1: Create an instance of ReclamationService
        /*SponsorService rs = new SponsorService();

        // Step 2: Create an instance of ReclamationService
        //Define the ID of the event you want to update
        int SponsorId = 2; // You must provide the existing Event ID


        // Define the updated data for the reclamation
        String UpdateNom = "vitalait";
        String UpdateAdresse = "France";
        String UpdateMail = "vitalait@gmail.com";


        // Create a new Event instance with the existing ID and update Data
        Sponsor sponorUpdate = new Sponsor(SponsorId,UpdateNom,UpdateAdresse,UpdateMail);

        // Step 3: Call the update method with the Reclamation instance
        try {
            rs.update(sponorUpdate);
            System.out.println("Reclamation updated Successfully.");
        } catch (SQLException e){
          System.err.println("Database error while updating reclamation: " + e.getMessage());
        }*/

        //--------DELETE----------
        //Step 1 : Create an instance of EventService
        /*SponsorService rs = new SponsorService();

        // Step 2: Specify the id of the reclamation you want to delete
        int sponsorIdToDelete = 5;

        //Step 3 : Call the delete method with the id
        try {
            rs.delete(sponsorIdToDelete);
            System.out.println("sponsor deleted successfully.");
        }catch (SQLException e){
            System.out.println("Database error while deleting reclamation" + e.getMessage());
        }*/



        //--------DISPLAY----------
        /*System.out.println(sp.getAll());*/

    }
}

