package tn.esprit.services;

import tn.esprit.entities.Suivi_Reclamation;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Suivi_ReclamationService<suivi_Reclamation> implements IService<Suivi_Reclamation> {

    Connection cnx = MaConnexion.getInstance().getCnx();


    public void add(Suivi_Reclamation suiviReclamation) throws SQLException {
        String requete = "INSERT INTO suivi_reclamation (idRec, status, description, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, suiviReclamation.getIdRec());
            pst.setString(2, suiviReclamation.getStatus());
            pst.setString(3, suiviReclamation.getDescription());
            pst.setString(4, suiviReclamation.getDate());
            pst.executeUpdate();
        }
    }


    public void update(Suivi_Reclamation suiviReclamation) throws SQLException {
        String requete = "UPDATE suivi_reclamation SET idRec = ?, status = ?, description = ?, date = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, suiviReclamation.getIdRec());
            pst.setString(2, suiviReclamation.getStatus());
            pst.setString(3, suiviReclamation.getDescription());
            pst.setString(4, suiviReclamation.getDate());
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

    public List<Suivi_Reclamation> getAllSuiviReclamations() {
        List<Suivi_Reclamation> suiviReclamationList = new ArrayList<>();

        String requete = "SELECT id, idRec, status, description, date FROM suivi_reclamation";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Suivi_Reclamation suiviRec = new Suivi_Reclamation();
                suiviRec.setId(rs.getInt("id"));
                suiviRec.setIdRec(rs.getInt("idRec"));
                suiviRec.setStatus(rs.getString("status"));
                suiviRec.setDescription(rs.getString("description"));
                suiviRec.setDate(rs.getString("date"));

                suiviReclamationList.add(suiviRec);
            }
        } catch (SQLException ex) {
            System.err.println("Error during query execution: " + ex.getMessage());
        }

        return suiviReclamationList;
    }
    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from suivi_reclamation where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }
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
    public List<Suivi_Reclamation> show() throws SQLException {

        return null;
    }


    @Override
    public Suivi_Reclamation getOne(int id) {
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




    public List<Suivi_Reclamation> chercher(String t) {
        List<Suivi_Reclamation> results = new ArrayList<>();
        try {
            String req = "SELECT * FROM suivi_reclamation WHERE status LIKE ?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, "%" + t + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Suivi_Reclamation suiviRec = new Suivi_Reclamation();
                suiviRec.setId(rs.getInt("id"));
                suiviRec.setIdRec(rs.getInt("idRec"));
                suiviRec.setStatus(rs.getString("status"));
                suiviRec.setDescription(rs.getString("description"));
                suiviRec.setDate(rs.getString("date"));
                results.add(suiviRec);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return results;
    }
    public List<Suivi_Reclamation> trier(String colonne, String ordre) throws SQLException {
        List<Suivi_Reclamation> results = new ArrayList<>();

        String query = "SELECT * FROM suivi_reclamation ORDER BY " + colonne + " " + ordre;
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Suivi_Reclamation suiviReclamation = new Suivi_Reclamation();
                suiviReclamation.setId(rs.getInt("id"));
                suiviReclamation.setIdRec(rs.getInt("idRec"));
                suiviReclamation.setStatus(rs.getString("status"));
                suiviReclamation.setDescription(rs.getString("description"));
                suiviReclamation.setDate(rs.getString("date"));
                results.add(suiviReclamation);
            }
        }

        return results;
    }


}
