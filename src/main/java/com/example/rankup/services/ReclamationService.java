package com.example.rankup.services;

import com.example.rankup.entities.Reclamation;
import com.example.rankup.interfaces.IService;
import com.example.rankup.util.MaConnexion;

import java.sql.*;
import java.util.*;

public class ReclamationService implements IService<Reclamation> {
    // var
    Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public void add(Reclamation reclamation) throws SQLException {
        // Define the SQL query with placeholders for the parameters
        String sql = "INSERT INTO `reclamation` (id, Nom, NumTel, type, description, date, id_user) VALUES (?,?,?, ?, ?, ?, ?)";

        // Create a PreparedStatement using the SQL query
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);

        preparedStatement.setInt(1, reclamation.getNumTel());
        preparedStatement.setString(2, reclamation.getNom());
        preparedStatement.setInt(3, reclamation.getNumTel());
        preparedStatement.setString(4, reclamation.getType());
        preparedStatement.setString(5, reclamation.getDescription());
        preparedStatement.setString(6, reclamation.getDate());
        preparedStatement.setInt(7, reclamation.getUserId());

        // Execute the update
        preparedStatement.executeUpdate();

        // Print a success message
        System.out.println("Reclamation sent successfully");
    }


    @SuppressWarnings("JpaQueryApiInspection")
    @Override

    public void update(Reclamation reclamation) throws SQLException {
        // Define the SQL query with placeholders for the parameters
        String sql = "UPDATE reclamation SET nom = ?, numTel = ?, type = ?, description = ?, date = ? WHERE id = ?";

        // Create a PreparedStatement using the SQL query
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);

        // Set the parameters using the appropriate set methods
        preparedStatement.setString(1, reclamation.getNom());
        preparedStatement.setInt(2, reclamation.getNumTel());
        preparedStatement.setString(3, reclamation.getType());
        preparedStatement.setString(4, reclamation.getDescription());
        preparedStatement.setString(5, reclamation.getDate());

        // Set the `id` parameter in the `WHERE` clause
        preparedStatement.setInt(6, reclamation.getId());

        // Execute the update
        preparedStatement.executeUpdate();

        // Optional: You can print a success message or return some information if needed
        System.out.println("Reclamation updated successfully.");
    }


    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from reclamation where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Reclamation> show() throws SQLException {
        List<Reclamation> reclamationsList = new ArrayList<>();
        String requete = "SELECT * FROM reclamation";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Reclamation rec = new Reclamation();  // Ensure this is inside the while loop
                rec.setId(rs.getInt("id"));
                rec.setNom(rs.getString("Nom").trim());
                rec.setNumTel(rs.getInt("NumTel"));
                rec.setType(rs.getString("type").trim());
                rec.setDescription(rs.getString("description").trim());
                rec.setDate(rs.getDate("date").toString().trim());

                reclamationsList.add(rec);
            }
        } catch (SQLException ex) {
            System.err.println("Error during query execution: " + ex.getMessage());
        }
        return reclamationsList;
    }

    public void deleteReclamation(int id) throws SQLException {
        String sql = "DELETE FROM reclamation WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A reclamation was deleted successfully!");
            } else {
                System.out.println("No reclamation was found with the ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error during the deletion: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Reclamation getOne(int id) {
        Reclamation reclamation = null;
        String requete = "SELECT * FROM reclamation WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    reclamation = new Reclamation();
                    reclamation.setId(rs.getInt("id"));
                    reclamation.setNom(rs.getString("Nom"));
                    reclamation.setNumTel(rs.getInt("NumTel"));
                    reclamation.setType(rs.getString("type"));
                    reclamation.setDescription(rs.getString("description"));
                    reclamation.setDate(rs.getString("date"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error occurred during getOne operation: " + ex.getMessage());
        }
        return reclamation;
    }
    @Override
    public ResultSet Getall() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `reclamation`";
            PreparedStatement st = cnx.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;
    }




    public List<Reclamation> afficherRec() {
        List<Reclamation> reclamationsList = new ArrayList<>();
        String requete = "SELECT id, Nom, NumTel, type, description, date FROM reclamation";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Reclamation rec = new Reclamation();  // Ensure this is inside the while loop
                rec.setId(rs.getInt("id"));
                rec.setNom(rs.getString("Nom").trim());
                rec.setNumTel(rs.getInt("NumTel"));
                rec.setType(rs.getString("type").trim());
                rec.setDescription(rs.getString("description").trim());
                rec.setDate(rs.getDate("date").toString().trim());

                reclamationsList.add(rec);
            }
        } catch (SQLException ex) {
            System.err.println("Error during query execution: " + ex.getMessage());
        }
        return reclamationsList;
    }





    public List<Reclamation> chercher(String searchText) throws SQLException {
        List<Reclamation> results = new ArrayList<>();

        String query = "SELECT * FROM reclamation WHERE Nom LIKE ? OR NumTel LIKE ? OR type LIKE ? OR description LIKE ? OR date LIKE ?";
        try (PreparedStatement st = cnx.prepareStatement(query)) {
            for (int i = 1; i <= 5; i++) {
                st.setString(i, "%" + searchText + "%");
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setNom(rs.getString("Nom"));
                reclamation.setNumTel(Integer.parseInt(rs.getString("NumTel")));
                reclamation.setType(rs.getString("type"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setDate(rs.getString("date"));
                results.add(reclamation);
            }
        }

        return results;
    }
    public List<Reclamation> trier(String colonne, String ordre) throws SQLException {
        List<Reclamation> results = new ArrayList<>();

        String query = "SELECT * FROM reclamation ORDER BY " + colonne + " " + ordre;
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setNom(rs.getString("Nom"));
                reclamation.setNumTel(Integer.parseInt(rs.getString("NumTel")));
                reclamation.setType(rs.getString("type"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setDate(rs.getString("date"));
                results.add(reclamation);
            }
        }

        return results;
    }

}
