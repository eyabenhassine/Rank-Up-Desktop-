package com.example.rankup.services;


import com.example.rankup.entities.MapEntity;
import com.example.rankup.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapService {
    private Connection connection = DataSource.getInstance().getCnx();

//    @Override
    public void ajouter(MapEntity mapEntity) {
        String query = "INSERT INTO map_entity(latitude, longitude) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(mapEntity.getLatitude()));
            preparedStatement.setString(2, String.valueOf(mapEntity.getLongitude()));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public MapEntity getLastElement() {
        String query = "SELECT * FROM map_entity ORDER BY id DESC LIMIT 1";
        MapEntity mapEntity = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                double latitude = Double.parseDouble(resultSet.getString("latitude"));
                double longitude = Double.parseDouble(resultSet.getString("longitude"));
                mapEntity = new MapEntity(latitude, longitude);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return mapEntity;
    }
}
