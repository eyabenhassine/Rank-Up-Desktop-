package com.example.rankup.services;

import com.example.rankup.entities.Categorie;
import com.example.rankup.interfaces.IService;
import com.example.rankup.utile.maConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements IService<Categorie> {

    Connection cnx = maConnexion.getInstance().getCnx();

    @Override
    public void add(Categorie categorie) throws SQLException {
        if (categorie.isValid()) {
            String req = "INSERT INTO categorie (nom_categorie, type, image) VALUES (?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, categorie.getNom_categorie());
            ps.setString(2, categorie.getType());
            ps.setString(3, categorie.getImage());// Utilisation de l'URL de l'image
            ps.executeUpdate();
            System.out.println("Catégorie ajoutée avec succès.");
        } else {
            System.out.println("Impossible d'ajouter la catégorie. Données non valides.");
        }
    }

    public void update(Categorie categorie) throws SQLException {
        String req = "UPDATE categorie SET nom_categorie = ?, type = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, categorie.getNom_categorie());
        ps.setString(2, categorie.getType());
        ps.setInt(3, categorie.getId());
        ps.executeUpdate();
        System.out.println("Catégorie mise à jour avec succès.");
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Categorie> getAll() {
        return List.of();
    }

    public void deleteC(int id) throws SQLException {
        String sql = "DELETE FROM categorie WHERE id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Catégorie supprimée avec succès.");
    }


    public List<Categorie> getAllCategories() {
        List<Categorie> categorieList = new ArrayList<>();
        String requete = "SELECT id, nom_categorie, type, image FROM categorie";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id"));
                categorie.setNom_categorie(rs.getString("nom_categorie"));
                categorie.setType(rs.getString("type"));
                categorie.setImage(rs.getString("image"));   // Add this line to set the image URL
                categorieList.add(categorie);
            }
        } catch (SQLException ex) {
            System.err.println("Error during query execution: " + ex.getMessage());
        }

        return categorieList;
    }

//    @Override
    public List<Categorie> show() throws SQLException {
        return null;
    }

    public Categorie getOne(int id) {
        String req = "SELECT * FROM categorie WHERE id = ?";
        Categorie categorie = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nom_categorie = resultSet.getString("nom_categorie");
                    String type = resultSet.getString("type");
                    String image = resultSet.getString("image");

                    categorie = new Categorie(id, nom_categorie, type, image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorie;
    }
}


