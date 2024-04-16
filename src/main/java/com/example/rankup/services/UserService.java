package com.example.rankup.services;

import com.example.rankup.entities.User;
import com.example.rankup.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;
import org.mindrot.jbcrypt.BCrypt;

public class UserService implements Iservice<User>
{

    private LocalDate birthdate;
    static Connection cnx = DataSource.getInstance().getCnx();
    public static boolean register(User user) {
        try {
            // Check if email already exists
            if (emailExists(user.getEmail())) {
                System.out.println("Email already exists!");
                return false;
            }

            // Hash the password using BCrypt
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            String query = "INSERT INTO user (email, username, password, phone, birthdate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.setString(4, user.getPhone());
                preparedStatement.setDate(5, java.sql.Date.valueOf(user.getBirthdate()));

                // Execute the insert statement
                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return false;
        }
    }


    @Override
    public void ajouter(User user) {

    }

    @Override
    public void modifier(User user) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<User> getAll() {
        return null;
    }

    @Override
    public User getOneByID(int id) {
        return null;
    }
    public static boolean emailExists(String email) {
        try {
            String query = "SELECT COUNT(*) FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return false;
    }
}
