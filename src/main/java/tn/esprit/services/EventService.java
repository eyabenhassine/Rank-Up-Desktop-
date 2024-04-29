package tn.esprit.services;

import tn.esprit.entities.Event;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
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
     public void insert (Event event){
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
     /*public void insert(Event event) {
         String req = "INSERT INTO `evenement` (`nom_event`, `date_debut`, `date_fin`, `type`, `description`) VALUES (?,?,?,?,?)";

         try {
             PreparedStatement ps = cnx.prepareStatement(req);
             ps.setString(1, event.getNom_event());
             ps.setDate(2, Date.valueOf(event.getDate_debut())); // Utilisez setDate pour les dates
             ps.setDate(3, Date.valueOf(event.getDate_fin()));   // Utilisez setDate pour les dates
             ps.setString(4, event.getType());
             ps.setString(5, event.getDescription());
             ps.executeUpdate();
             System.out.println("Event inserted successfully.");
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }*/
     /*public void insert(Event event) {
         // Vérifie si le champ 'nom_event' est null ou vide
         if (event.getNom_event() == null || event.getNom_event().isEmpty()) {
             // Affiche un message d'erreur ou lance une exception selon votre logique d'application
             System.err.println("Error: 'nom_event' cannot be null or empty.");
             // Vous pouvez également lancer une exception ici pour arrêter le traitement
             throw new IllegalArgumentException("'nom_event' cannot be null or empty.");
         }

         String req = "INSERT INTO `evenement` (`nom_event`, `date_debut`, `date_fin`, `type`, `description`) VALUES (?,?,?,?,?)";

         try {
             PreparedStatement ps = cnx.prepareStatement(req);
             ps.setString(1, event.getNom_event());
             // Vérifiez si les champs de date sont vides avant de les convertir en java.sql.Date
             if (event.getDate_debut() != null && !event.getDate_debut().isEmpty()) {
                 ps.setDate(2, Date.valueOf(event.getDate_debut()));
             } else {
                 ps.setNull(2, Types.DATE);
             }
             if (event.getDate_fin() != null && !event.getDate_fin().isEmpty()) {
                 ps.setDate(3, Date.valueOf(event.getDate_fin()));
             } else {
                 ps.setNull(3, Types.DATE);
             }
             ps.setString(4, event.getType());
             ps.setString(5, event.getDescription());
             ps.executeUpdate();
             System.out.println("Event inserted successfully.");
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }*/



    /*public void add(Event event) throws SQLException {
        // Define the SQL query with placeholders for the parameters
        String sql = "INSERT INTO `evenement` (nom_event,Date_debut, date_fin,type, description) VALUES (?, ?, ?, ?, ?)";

        // Create a PreparedStatement using the SQL query
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);

        preparedStatement.setString(1, event.getNom_event());
        preparedStatement.setString(2, event.getDate_debut());
        preparedStatement.setString(3, event.getDate_fin());
        preparedStatement.setString(4, event.getType());
        preparedStatement.setString(5, event.getDescription());



        // Execute the update
        preparedStatement.executeUpdate();

        // Print a success message
        System.out.println("Reclamation sent successfully");
    }*/




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
        List <Event> events = new ArrayList<>();


        try {
            String req = "SELECT * FROM evenement";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){

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
}
