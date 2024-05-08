package com.example.rankup.services;

import com.example.rankup.entities.Reservation;
import com.example.rankup.interfaces.IService;
import com.example.rankup.util.MaConnexion;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Reservation reservation) {
        String req = "INSERT INTO reservation(`date`, `description`) VALUES ('"+reservation.getDate()+"','"+reservation.getDescription()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void update(Reservation reservation) {
        String req = "UPDATE reservation SET date = ?, description = ? WHERE id = ?";
        PreparedStatement pr = null;
        try {
            pr = cnx.prepareStatement(req);
            pr.setObject(1, reservation.getDate());
            pr.setString(2, reservation.getDescription());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM reservation WHERE id = ?";

        try {
            PreparedStatement pr = cnx.prepareStatement(req);
            pr.setInt(1,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reslist = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Reservation reso = new Reservation();
                reso.setId(res.getInt("id"));
                reso.setDate(res.getDate(2));
                reso.setDescription(res.getString(3));
                reslist.add(reso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reslist;
    }

    @Override
    public Reservation getOne(int id) {
        return null;
    }
}
