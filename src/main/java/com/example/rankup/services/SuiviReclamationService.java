package com.example.rankup.services;

import com.example.rankup.interfaces.IService;
import com.example.rankup.entities.Reclamation;
import com.example.rankup.entities.SuiviReclamation;
import com.example.rankup.util.MaConnexion;
import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuiviReclamationService implements IService<SuiviReclamation> {

    Connection cnx = MaConnexion.getInstance().getCnx();


    public void add(SuiviReclamation suiviReclamation) throws SQLException {
        String requete = "INSERT INTO suivi_reclamation (idRec, status, description, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, suiviReclamation.getReclamation().getId());
            pst.setString(2, suiviReclamation.getStatus());
            pst.setString(3, suiviReclamation.getDescription());
            pst.setDate(4, new Date(suiviReclamation.getDate().getTime())); // Convert java.util.Date to java.sql.Date
            pst.executeUpdate();
        }
    }



    @Override
    public List<SuiviReclamation> show() throws SQLException {
        List<SuiviReclamation> suiviReclamationList = new ArrayList<>();

        String requete = "SELECT * FROM suivi_reclamation";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                SuiviReclamation suiviRec = new SuiviReclamation();
                suiviRec.setId(rs.getInt("id"));
                suiviRec.setStatus(rs.getString("status"));
                suiviRec.setDescription(rs.getString("description"));
                suiviRec.setDate(rs.getDate("date"));

                // Use getOne from ReclamationService to fetch associated Reclamation
                Reclamation rec = new ReclamationService().getOne(rs.getInt("idRec"));
                if (rec != null) {
                    suiviRec.setReclamation(rec);
                    suiviReclamationList.add(suiviRec);
                } else {
                    // Handle if reclamation doesn't exist
                    System.err.println("Error: Reclamation not found for SuiviReclamation ID: " + rs.getInt("idRec"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error during query execution: " + ex.getMessage());
        }

        return suiviReclamationList;
    }


    @Override
    public SuiviReclamation getOne(int id) {
        SuiviReclamation suiviRec = null;
        String requete = "SELECT * FROM suivi_reclamation WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    suiviRec = new SuiviReclamation();
                    suiviRec.setId(rs.getInt("id"));
                    suiviRec.setStatus(rs.getString("status"));
                    suiviRec.setDescription(rs.getString("description"));
                    suiviRec.setDate(rs.getDate("date"));

                    // Use getOne from ReclamationService to fetch associated Reclamation
                    Reclamation rec = new ReclamationService().getOne(rs.getInt("idRec"));
                    if (rec != null) {
                        suiviRec.setReclamation(rec);
                    } else {
                        // Handle if reclamation doesn't exist
                        System.err.println("Error: Reclamation not found for SuiviReclamation ID: " + rs.getInt("idRec"));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred during getOne operation: " + ex.getMessage());
        }
        return suiviRec;
    }




    public void update(SuiviReclamation suiviReclamation) throws SQLException {
        String requete = "UPDATE suivi_reclamation SET idRec = ?, status = ?, description = ?, date = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, suiviReclamation.getReclamation().getId());
            pst.setString(2, suiviReclamation.getStatus());
            pst.setString(3, suiviReclamation.getDescription());
            pst.setDate(4, new Date(suiviReclamation.getDate().getTime())); // Convert java.util.Date to java.sql.Date
            pst.setInt(5, suiviReclamation.getId());

            int rowsAffected = pst.executeUpdate();
            System.out.println("Update operation rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Suivi_Reclamation updated successfully.");
            } else {
                System.out.println("No Suivi_Reclamation was updated, check your WHERE clause conditions!");
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred during update operation: " + e.getMessage());
            throw e;
        }
    }


    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from suivi_reclamation where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }
    public List<SuiviReclamation> chercher(String t) {
        List<SuiviReclamation> results = new ArrayList<>();
        try {
            String req = "SELECT * FROM suivi_reclamation WHERE status LIKE ?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, "%" + t + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SuiviReclamation suiviRec = new SuiviReclamation();
                suiviRec.setId(rs.getInt("id"));
                Reclamation rec = new ReclamationService().getOne(rs.getInt("idRec"));
                if (rec != null) {
                    suiviRec.setReclamation(rec);
                } else {
                    // Handle if reclamation doesn't exist
                    System.err.println("Error: Reclamation not found for SuiviReclamation ID: " + rs.getInt("idRec"));
                }                suiviRec.setStatus(rs.getString("status"));
                suiviRec.setDescription(rs.getString("description"));
                suiviRec.setDate(rs.getDate("date"));
                results.add(suiviRec);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return results;
    }
    @Override
    public ResultSet Getall() {
        return null;
    }
     /*
    public void deleteSuiviReclamation(int id) throws SQLException {
        String sql = "DELETE FROM suivi_reclamation WHERE id = ?";
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
    public List<SuiviReclamation> show() throws SQLException {

        return null;
    }


    @Override
    public SuiviReclamation getOne(int id) {
        return null;
    }

    @Override
    public ResultSet Getall() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `suivi_reclamation`";
            PreparedStatement st = cnx.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;    }





    public List<SuiviReclamation> trier(String colonne, String ordre) throws SQLException {
        List<SuiviReclamation> results = new ArrayList<>();

        String query = "SELECT * FROM suivi_reclamation ORDER BY " + colonne + " " + ordre;
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                SuiviReclamation suiviReclamation = new SuiviReclamation();
                suiviReclamation.setId(rs.getInt("id"));
                suiviReclamation.setIdRec(rs.getInt("idRec"));
                suiviReclamation.setStatus(rs.getString("status"));
                suiviReclamation.setDescription(rs.getString("description"));
                suiviReclamation.setDate(rs.getString("date"));
                results.add(suiviReclamation);
            }
        }

        return results;
    } */


}
