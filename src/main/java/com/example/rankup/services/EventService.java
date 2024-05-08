package com.example.rankup.services;

import com.example.rankup.entities.Event;
import com.example.rankup.interfaces.IService;
import com.example.rankup.util.MaConnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class EventService implements IService<Event> {
    //att
    Connection cnx = MaConnexion.getInstance().getCnx();

    public EventService() throws SQLException {
    }


    //actions
    @Override
    public void add(Event event) {
        String req = "INSERT INTO `evenement` (`nom_event`, `date_debut`, `date_fin`, `type`, `description`) VALUES ('" +
                event.getNom_event() + "', '" +
                event.getDate_debut() + "', '" +
                event.getDate_fin() + "', '" +
                event.getType() + "', '" +
                event.getDescription() + "')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Event added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Event event) {
        String req = "INSERT INTO evenement (`nom_event`, `date_debut`, `date_fin`, `type`, `description`) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, event.getNom_event());
            ps.setString(2, event.getDate_debut());
            ps.setString(3, event.getDate_fin());
            ps.setString(4, event.getType());
            ps.setString(5, event.getDescription());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Event event) throws SQLException {
        // Define the SQL query with placeholders for the parameters
        String sql = "UPDATE evenement SET nom_event = ?, date_debut = ?, date_fin = ?, type = ?, description = ? WHERE id = ?";

        // Create a PreparedStatement using the SQL query
        PreparedStatement ps = cnx.prepareStatement(sql);

        //Set the parametrs using the appropriate set methods
        ps.setString(1, event.getNom_event());
        ps.setString(2, event.getDate_debut());
        ps.setString(3, event.getDate_fin());
        ps.setString(4, event.getType());
        ps.setString(5, event.getDescription());

        // Set the `id` parameter in the `WHERE` clause
        ps.setInt(6, event.getId());

        // Execute the update
        ps.executeUpdate();

        // Optional: You can print a success message or return some information if needed
        System.out.println("Event updated successfully.");

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "Delete from evenement where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    public void deleteEvent(int id) throws SQLException {
        String sql = "DELETE FROM evenement WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Un événement a été supprimé avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
            throw e;
        }
    }


    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();


        try {
            String req = "SELECT * FROM evenement";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {

                Event event = new Event();
                event.setId(res.getInt("id"));
                event.setNom_event(res.getString(2));
                event.setDate_debut(res.getDate(3).toString().trim());
                event.setDate_fin(res.getDate(4).toString().trim());
                event.setType(res.getString("type"));
                event.setDescription(res.getString("description"));

                events.add(event);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    public List<Event> afficherEvents() {
        List<Event> eventsList = new ArrayList<>();
        String requete = "SELECT  id,nom_event, date_debut, date_fin, type, description FROM evenement";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Event event = new Event();  // Assurez-vous que cette ligne soit à l'intérieur de la boucle while
                event.setId(rs.getInt("id"));
                event.setNom_event(rs.getString("nom_event").trim());
                event.setDate_debut(rs.getString("date_debut").trim());
                event.setDate_fin(rs.getString("date_fin").trim());
                event.setType(rs.getString("type").trim());
                event.setDescription(rs.getString("description").trim());

                eventsList.add(event);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'exécution de la requête : " + ex.getMessage());
        }
        return eventsList;
    }

    @Override
    public Event getOne(int id) {

        return null;
    }


    public List<Event> chercher(String searchText) throws SQLException {
        List<Event> results = new ArrayList<>();

        String query = "SELECT * FROM evenement WHERE nom_event LIKE ? OR date_debut LIKE ? OR date_fin LIKE ? OR type LIKE ? OR description LIKE ?";
        try (PreparedStatement st = cnx.prepareStatement(query)) {
            for (int i = 1; i <= 5; i++) {
                st.setString(i, "%" + searchText + "%");
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Event ev = new Event();
                ev.setId(rs.getInt("id"));
                ev.setNom_event(rs.getString("nom_event"));
                ev.setDate_debut(rs.getString("date_debut"));
                ev.setDate_fin(rs.getString("date_fin"));
                ev.setType(rs.getString("type"));
                ev.setDescription(rs.getString("description"));

                results.add(ev);
            }
        }
        return results;

    }



    public List<Event> getEventsByDate(LocalDate date) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM evenement WHERE DATE(date_debut) = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setNom_event(resultSet.getString("nom_event"));
                event.setDate_debut(resultSet.getString("date_debut"));
                event.setDate_fin(resultSet.getString("date_fin"));
                event.setType(resultSet.getString("type"));
                event.setDescription(resultSet.getString("description"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }




}
