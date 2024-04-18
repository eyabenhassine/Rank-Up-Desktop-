package tn.esprit.services;

import tn.esprit.entities.Reclamation;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.*;

public class ReclamationService implements IService<Reclamation> {
    // var
    Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public void add(Reclamation reclamation) throws SQLException {
        // Define the SQL query with placeholders for the parameters
        String sql = "INSERT INTO `reclamation` (id,Nom, NumTel, type, description, date) VALUES (?,?, ?, ?, ?, ?)";

        // Create a PreparedStatement using the SQL query
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);

        preparedStatement.setInt(1, reclamation.getNumTel());
        preparedStatement.setString(2, reclamation.getNom());
        preparedStatement.setInt(3, reclamation.getNumTel());
        preparedStatement.setString(4, reclamation.getType());
        preparedStatement.setString(5, reclamation.getDescription());
        preparedStatement.setString(6, reclamation.getDate());

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return null;
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

    public ResultSet chercher(String t) {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM reclamation WHERE type LIKE '%" + t + "%'\n";
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReclamationService that = (ReclamationService) o;
        return Objects.equals(cnx, that.cnx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnx);
    }
}
