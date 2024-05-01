package tn.esprit.services;

import tn.esprit.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayersService {

    private Connection connection;

    public PlayersService() {
        connection = DBConnection.getInstance().getConnection();
    }

    public List<String> getPlayers() throws SQLException {
        String sql = "select username from players";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<String> players = new ArrayList<>();

        while (rs.next()) {
            players.add(rs.getString("username"));
        }
        return players;
    }
}
