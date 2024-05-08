package org.example.rankup.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface Iservice<T> {
    void add(T t) throws SQLException;
    void update(T t) throws SQLException;


    void deleteC(int id) throws SQLException;


    List<T> show() throws SQLException;



    //5: one
    T getOne(int id);


}