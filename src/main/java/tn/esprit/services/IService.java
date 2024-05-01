package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t, String s) throws SQLException;

    void supprimer(String id) throws SQLException;

    List<T> recuperer() throws SQLException;
}
