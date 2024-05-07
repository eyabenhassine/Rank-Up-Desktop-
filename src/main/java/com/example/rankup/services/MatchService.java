package com.example.rankup.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;
import com.example.rankup.entities.Equipe;
import com.example.rankup.entities.MatchEntity;
import com.example.rankup.entities.User;
import com.example.rankup.utils.DataSource;

public class MatchService implements Iservice<MatchEntity> {
    private Connection connection = DataSource.getInstance().getCnx();
    private EquipeService equipeService = new EquipeService();

    @Override
    public void ajouter(MatchEntity match) {
        String query = "INSERT INTO match_entity(id, date_debut) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, match.getId());
            preparedStatement.setString(2, match.getDate_debut().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void modifier(MatchEntity matchEntity) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<MatchEntity> getAll() {
        return Set.of();
    }

    public List<MatchEntity> getAllMatchEntities() {
        List<MatchEntity> matchEntities = new ArrayList<>();
        Map<String, MatchEntity> matchEntityMap = new HashMap<>();
        String query = "SELECT m.id, m.date_debut, e.id AS equipe_id, e.nom_equipe " +
                "FROM match_entity m " +
                "LEFT JOIN equipe e ON m.id = e.match_entity_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String matchEntityId = resultSet.getString("id");
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("date_debut"));

                MatchEntity matchEntity = matchEntityMap.get(matchEntityId);
                if (matchEntity == null) {
                    matchEntity = new MatchEntity(matchEntityId, dateDebut);
                    matchEntities.add(matchEntity);
                    matchEntityMap.put(matchEntityId, matchEntity);
                }

                String equipeId = resultSet.getString("equipe_id");
                String equipeNom = resultSet.getString("nom_equipe");
                if (equipeId != null) {
                    Equipe equipe = new Equipe (equipeId, equipeNom);
                    matchEntity.getEquipes().add(equipe);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return matchEntities;
    }


    @Override
    public MatchEntity getOneByID(int id) {
        return null;
    }
}
