package com.example.rankup.services;

import com.example.rankup.entities.Event;
import com.example.rankup.entities.Sponsor;
import com.example.rankup.interfaces.IService;
import com.example.rankup.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SponsorService implements IService<Sponsor> {

    //att
    Connection cnx = MaConnexion.getInstance().getCnx();

    public SponsorService() throws SQLException {
    }

    @Override
    public void add(Sponsor sponsor) {
        String req = "INSERT INTO sponsor (nom_sponsor, adresse_sponsor, mail_sponsor) VALUES ('" +
                sponsor.getNom_sponsor() + "','" +
                sponsor.getAdresse_sponsor() + "','" +
                sponsor.getMail_sponsor() + "')";

        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Sponsor added successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Sponsor sponsor) throws SQLException {
        // Define the SQL query with placeholders for the parameters
        String sql = "UPDATE sponsor SET nom_sponsor = ?, adresse_sponsor = ?, mail_sponsor = ? WHERE id = ?";

        // Create a PreparedStatement using the SQL query
        PreparedStatement ps = cnx.prepareStatement(sql);

        //Set the parameters using the appropriate set methods
        ps.setString(1,sponsor.getNom_sponsor());
        ps.setString(2,sponsor.getAdresse_sponsor());
        ps.setString(3,sponsor.getMail_sponsor());

        // Set the `id` parameter in the `WHERE` clause
        ps.setInt(4, sponsor.getId());

        // Execute the update
        ps.executeUpdate();

        // Optional: You can print a success message or return some information if needed
        System.out.println("Sponsor updated successfully.");

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM sponsor WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();

    }

    @Override
    public List<Sponsor> getAll() {
        List <Sponsor> sponsors = new ArrayList<>();


        try {
            String req = "SELECT * FROM sponsor";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Sponsor sponsor = new Sponsor();
                sponsor.setId(res.getInt("id"));
                sponsor.setNom_sponsor(res.getString(2));
                sponsor.setAdresse_sponsor(res.getString(3));
                sponsor.setMail_sponsor(res.getString("mail_sponsor"));
                sponsors.add(sponsor);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return sponsors;
    }

    @Override
    public Sponsor getOne(int id) {
        return null;
    }



    public List<Sponsor> afficherSponsors() {
        List<Sponsor> sponsorsList = new ArrayList<>();
        String requete = "SELECT id, nom_sponsor, adresse_sponsor, mail_sponsor FROM sponsor";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Sponsor sponsor = new Sponsor();  // Ensure this line is inside the while loop
                sponsor.setId(rs.getInt("id"));
                sponsor.setNom_sponsor(rs.getString("nom_sponsor").trim());
                sponsor.setAdresse_sponsor(rs.getString("adresse_sponsor").trim());
                sponsor.setMail_sponsor(rs.getString("mail_sponsor").trim());

                sponsorsList.add(sponsor);
            }
        } catch (SQLException ex) {
            System.err.println("Error executing query: " + ex.getMessage());
        }
        return sponsorsList;
    }



    public void deleteSponsor(int id) throws SQLException {
        String sql = "DELETE FROM sponsor WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Un sponsor a été supprimé avec succès !");
            } else {
                System.out.println("Aucun sponsor trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
            throw e;
        }
    }



    //actions


}
