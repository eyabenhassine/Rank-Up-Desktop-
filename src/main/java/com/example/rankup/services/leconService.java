package com.example.rankup.services;

import javafx.collections.FXCollections;
import com.example.rankup.entities.lecon;
import com.example.rankup.interfaces.IService;
import com.example.rankup.utile.maConnexion;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class leconService implements IService<lecon> {

    Connection cnx = maConnexion.getInstance().getCnx();

    public void add(lecon lecon) throws SQLException {
        if (lecon.isValid()) {
            String req = "INSERT INTO `lecon`(`nom_lecon`, `url`, `prix`, `description`) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, lecon.getNom_lecon());
            ps.setString(2, lecon.getUrl());
            ps.setInt(3, lecon.getPrix());
            ps.setString(4, lecon.getDescription());

            ps.executeUpdate();
            System.out.println("add with success ");
        } else {
            System.out.println("Failed to add lesson. Invalid data provided.");
        }
    }

    public void update(lecon lecon) throws SQLException {
        String req = "UPDATE lecon SET nom_lecon = ?, url = ?, prix = ?, description = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, lecon.getNom_lecon());
        ps.setString(2, lecon.getUrl());
        ps.setInt(3, (int) lecon.getPrix());
        ps.setString(4, lecon.getDescription());
        ps.setInt(5, lecon.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<lecon> getAll() {
        return List.of();
    }


    //    @Override
    public void deleteC(int id) throws SQLException {
        String sql = "delete from lecon where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

//    @Override
    public List<lecon> show() throws SQLException {
        return List.of();
    }




    public List<lecon> getall() {
        List<lecon> lecons = new ArrayList<>();
        String req = "SELECT * FROM lecon";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String nom_lecon = resultSet.getString("nom_lecon");
                String url = resultSet.getString("url");
                int prix = resultSet.getInt("prix");
                String description = resultSet.getString("description");

                lecon lecon = new lecon(nom_lecon, url, prix, description);
                lecons.add(lecon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecons;
    }


    public lecon getOne(int id) {
        String req = "SELECT * FROM lecon WHERE id = ?";
        lecon lecon = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nom_lecon = resultSet.getString("nom_lecon");
                    String url = resultSet.getString("url");
                    int prix = resultSet.getInt("prix");
                    String description = resultSet.getString("description");

                    lecon = new lecon(nom_lecon, url, prix, description);
                    lecon.setId(id); // Assurez-vous de définir l'ID de la leçon
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecon;
    }

    public List<lecon> afficherRec() {
        List<lecon> leconsList = new ArrayList<>();
        String requete = "SELECT id,nom_lecon, url, prix, description  FROM lecon";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                lecon rec = new lecon();  // Ensure this is inside the while loop
                rec.setId(rs.getInt("id"));
                rec.setNom_lecon(rs.getString("nom_lecon").trim());
                rec.setUrl(rs.getString("URL").trim());
                rec.setPrix(rs.getInt("PRIX"));
                rec.setDescription(rs.getString("description").trim());

                leconsList.add(rec);
            }
        } catch (SQLException ex) {
            System.err.println("Error during query execution: " + ex.getMessage());
        }
        return leconsList;
    }

    public List<lecon> chercherLecon(String chaine) {
        String sql = "SELECT * FROM lecon WHERE (nom_lecon LIKE ?   ) order by nom_lecon ";
        //Connection cnx= Maconnexion.getInstance().getCnx();
        String ch = "%" + chaine + "%";
        List<lecon> myList = FXCollections.observableArrayList();
        try {

            Statement ste = cnx.createStatement();
            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee = cnx.prepareStatement(sql);
            stee.setString(1, ch);


            ResultSet rs = stee.executeQuery();
            while (rs.next()) {
                lecon e = new lecon();

                e.setNom_lecon(rs.getString("nom_lecon"));
                e.setUrl(rs.getString("URL"));
                e.setPrix(rs.getInt("PRIX"));
                e.setDescription(rs.getString("description"));

                myList.add(e);
                System.out.println("LECON trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<lecon> recuperer() throws SQLException {
        String sql = "SELECT * FROM lecon WHERE prix>0";
        try (Statement statement = cnx.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            List<lecon> list = new ArrayList<>();
            while (rs.next()) {
                lecon p = new lecon(
                        rs.getString("nom_lecon"),
                        rs.getString("URl"),
                        rs.getInt("PRIX"),
                        rs.getString("description")
                );
                list.add(p);
            }
            return list;
        }
    }
}























