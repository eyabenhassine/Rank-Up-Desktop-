package tn.esprit.services;

import tn.esprit.entities.Subscription_plan;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Subscription_PlanService implements IService <Subscription_plan> {

    Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public void add(Subscription_plan subscriptionPlan) {
        String req = "INSERT INTO subscription_plan (`type`, `prix`, `additional_info`) VALUES ('"+subscriptionPlan.getType()+"','"+subscriptionPlan.getPrix()+"','"+subscriptionPlan.getAdditional_info()+"')";
        try  {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public void update1(Subscription_plan subscriptionPlan) {
        String req = "UPDATE `subscription_plan` SET `type`='" + subscriptionPlan.getType() + "', `prix`='" + subscriptionPlan.getPrix() + "', `additional_info`='" + subscriptionPlan.getAdditional_info() + "' WHERE `id`=" + subscriptionPlan.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Sub_plan added successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/


    @Override
    public void update (Subscription_plan subscriptionPlan) {
        String req = "UPDATE subscription_plan SET type = ?, prix = ?, additional_info = ? WHERE id = ?";
        PreparedStatement pr = null;
        try {
            pr = cnx.prepareStatement(req);
            pr.setString(1, subscriptionPlan.getType());
            pr.setFloat(2, subscriptionPlan.getPrix());
            pr.setString(3, subscriptionPlan.getAdditional_info());
            pr.setInt(4, subscriptionPlan.getId());

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public void delete (int id)  {
        String req = "DELETE FROM subscription_plan WHERE id = ?";
        try  {
            PreparedStatement pr = cnx.prepareStatement(req);
            pr.setInt(1,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<Subscription_plan> getAll() {
        List<Subscription_plan> subplans = new ArrayList<>();

        String req = "SELECT * FROM Subscription_plan";
        try {
            Statement stt = cnx.createStatement();
            ResultSet res = stt.executeQuery(req);
            while (res.next()) {
                Subscription_plan subplan = new Subscription_plan();
                subplan.setId(res.getInt("id"));
                subplan.setType(res.getString(2));
                subplan.setPrix(res.getFloat(3));
                subplan.setAdditional_info(res.getString("additional_info"));
                subplans.add(subplan);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subplans;

    }

    @Override
    public Subscription_plan getOne(int id) {
        return null;
    }


    public List<Subscription_plan> chercher(String searchText) throws SQLException {
        List<Subscription_plan> results = new ArrayList<>();

        String query = "SELECT * FROM subscription_plan WHERE type = ?, prix = ?, additional_info = ? WHERE id = ?";
        try (PreparedStatement st = cnx.prepareStatement(query)) {
            for (int i = 1; i <= 5; i++) {
                st.setString(i, "%" + searchText + "%");
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Subscription_plan ev = new Subscription_plan();
                ev.setId(rs.getInt("id"));
                ev.setType(rs.getString("type"));
                ev.setPrix(rs.getFloat("prix"));


                results.add(ev);
            }
        }
        return results;

    }



}
