package tn.esprit.services;

import javafx.scene.control.Alert;
import tn.esprit.models.Equipe;
import tn.esprit.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeService implements IService<Equipe> {
    private Connection connection;

    public EquipeService() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public void ajouter(Equipe equipe) throws SQLException {
//        String sql = "insert into equipe (nom,prenom,age)"
//                +"values('"+equipe.getNom() +"','"+equipe.getPrenom() +"'," +
//                +equipe.getAge() +")";
//        Statement st = connection.createStatement();
//        st.executeUpdate(sql);

        String sql = "INSERT INTO equipe(nom_equipe)"
                +" VALUES('"+equipe.getNom()+"')";
//        ",'"+equipe.getJoueurs()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(sql);
    }

    @Override
    public void modifier(Equipe equipe, String s) throws SQLException {
        String sql = "update equipe set nom_equipe=? where nom_equipe=?";
        try {
            PreparedStatement ste = connection.prepareStatement(sql);
            System.out.println(s);
            System.out.println(equipe.getNom());
            ste.setString(1, equipe.getNom());
            ste.setString(2, s);
            ste.executeUpdate();
            Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("Equipe updated successfully");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(String nomeq) throws SQLException {
        String sql = "delete from equipe where nom_equipe=?";
        try {
            PreparedStatement ste = connection.prepareStatement(sql);
            ste.setString(1, nomeq);
            ste.executeUpdate();
            Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("Equipe deleted successfully");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Equipe> recuperer() throws SQLException {
        String sql = "select nom_equipe from equipe";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<Equipe> equipes = new ArrayList<>();

        while (rs.next()){
            Equipe e = new Equipe();
            e.setNom(rs.getString("nom_equipe"));

            equipes.add(e);
        }
        return equipes;
    }
}
