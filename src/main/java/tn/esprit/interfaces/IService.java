package tn.esprit.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IService  <T> {
    //CRUD
    //1
    void add(T t) throws SQLException;
    //2
    void update(T t) throws SQLException;
    //3
    void delete(int id) throws SQLException;
    //4
    List<T> show() throws SQLException;
    //5: one
    T getOne(int id);
    public ResultSet Getall();
}
