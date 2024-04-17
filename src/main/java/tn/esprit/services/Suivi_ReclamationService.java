package tn.esprit.services;

import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Suivi_Reclamation;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.List;

public class Suivi_ReclamationService  implements IService<Suivi_Reclamation> {

    Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public void add(Suivi_Reclamation suiviReclamation) throws SQLException {

        String req = "INSERT INTO `suivi_reclamation`( `idRec`,`status`, `description`, `date`) VALUES ('" + suiviReclamation.getIdRec() + "','" + suiviReclamation.getStatus() + "','" + suiviReclamation.getDescription() + "','" + suiviReclamation.getDate() + "')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Repense sent Successfully");
    }

    @Override
    public void update(Suivi_Reclamation suiviReclamation) throws SQLException {

        String sql = "Update suivi_reclamation set idRec= ?, type= ?, description= ? , date= ? where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(suiviReclamation.getIdRec()));
        preparedStatement.setString(2, suiviReclamation.getStatus());
        preparedStatement.setString(3, suiviReclamation.getDescription());
        preparedStatement.setString(4, suiviReclamation.getDate());

        preparedStatement.executeUpdate();


    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from suivi_reclamation where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Suivi_Reclamation> show() throws SQLException {
        return null;
    }

    @Override
    public Suivi_Reclamation getOne(int id) {
        return null;
    }

    @Override
    public ResultSet Getall() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `suivi_reclamation`";
            PreparedStatement st = cnx.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;
    }
    public ResultSet chercher(String t) {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM suuivi_reclamation WHERE status LIKE '%"+t+"%'\n";
            PreparedStatement st = cnx.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;
    }

}
