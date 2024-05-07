package com.example.rankup.services;

import java.util.*;
import java.util.UUID;

import com.example.rankup.entities.User;
import javafx.scene.control.Alert;
import com.example.rankup.entities.Equipe;
import com.example.rankup.utils.DataSource;

import java.sql.*;

public class EquipeService implements Iservice<Equipe> {

//    static Connection connection;
    private Connection connection = DataSource.getInstance().getCnx();
    private UserService userService = new UserService();

    public EquipeService() {
//        connection = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouter(Equipe equipe) {
        String query = "INSERT INTO equipe(id, nom_equipe) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, equipe.getId());
            preparedStatement.setString(2, equipe.getNom());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void modifier(Equipe equipe) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<Equipe> getAll() {
        return null;
    }

    public List<Equipe> getAllEquipe() {
        List<Equipe> equipes = new ArrayList<>();
        String query = "SELECT id, nom_equipe FROM equipe";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom_equipe");

                Equipe equipe = new Equipe(id, nom);
                equipes.add(equipe);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return equipes;
    }

    public List<Equipe> getAllEquipesWithUsers() {
        List<Equipe> equipes = new ArrayList<>();
        Map<String, Equipe> equipeMap = new HashMap<>();
        String query = "SELECT e.id, e.nom_equipe, u.id AS user_id " +
                "FROM equipe e " +
                "LEFT JOIN user u ON e.id = u.equipe_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String equipeId = resultSet.getString("id");
                String equipeNom = resultSet.getString("nom_equipe");

                Equipe equipe = equipeMap.get(equipeId);
                if (equipe == null) {
                    equipe = new Equipe(equipeId, equipeNom);
                    equipes.add(equipe);
                    equipeMap.put(equipeId, equipe);
                }

                String userId = resultSet.getString("user_id");
                if (userId != null) {
                    User user = userService.getOneByID(Integer.parseInt(userId));
                    equipe.getUsers().add(user);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return equipes;
    }

    @Override
    public Equipe getOneByID(int id) {
        return null;
    }

    public void modifierMatch_id(Equipe e) {
        String query = "UPDATE equipe SET match_entity_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, e.getMatch_id());
            preparedStatement.setString(2, e.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
        }
        catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }
}
