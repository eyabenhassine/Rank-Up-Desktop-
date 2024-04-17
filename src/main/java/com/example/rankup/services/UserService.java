package com.example.rankup.services;

import com.example.rankup.entities.User;
import com.example.rankup.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.mindrot.jbcrypt.BCrypt;

public class UserService implements Iservice<User>
{

    private LocalDate birthdate;
    static Connection cnx = DataSource.getInstance().getCnx();
    public static boolean register(User user) {
        try {

            if (emailExists(user.getEmail())) {
                System.out.println("Email already exists!");
                return false;
            }


            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            String roles = String.join(",", user.getRoles());

            String query = "INSERT INTO user (email, username, password, phone, birthdate , roles) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.setString(4, user.getPhone());
                preparedStatement.setDate(5, java.sql.Date.valueOf(user.getBirthdate()));
                preparedStatement.setString(6, roles);


                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserAccount(int userId) {
        try {
            String query = "DELETE FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("User account deleted successfully");
                    return true;
                } else {
                    System.out.println("Failed to delete user account");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean loginUser(String email, String password) {
        try {
            String query = "SELECT id, password FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("password");
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            // Password is correct, set user session
                            int userId = resultSet.getInt("id");
                            SessionManager.setSession("userId", userId);
                            return true; // Login successful
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return false; // Login failed
    }

    public User fetchPlayerData() {
        User user = null;
        try {
            int userId = (int) SessionManager.getSession("userId");

            String query = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setEmail(resultSet.getString("email"));
                        user.setFirstname(resultSet.getString("firstname"));
                        user.setLastname(resultSet.getString("lastname"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
                        user.setElo(resultSet.getString("elo"));
                        user.setBio(resultSet.getString("bio"));
                        user.setSummonername(resultSet.getString("summonername"));




                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean editUserInfo(User user) {
        try {
            // Retrieve the user ID from the session
            int userId = (int) SessionManager.getSession("userId");

            // Check if the user ID is valid
            if (userId <= 0) {
                System.out.println("Invalid session");
                return false;
            }

            // Update user information in the database
            String query = "UPDATE user SET firstname = ?, lastname = ?, username = ?, phone = ?, birthdate = ?, summonername = ?, bio = ?, elo = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, user.getFirstname());
                preparedStatement.setString(2, user.getLastname());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPhone());
                preparedStatement.setDate(5, java.sql.Date.valueOf(user.getBirthdate()));
                preparedStatement.setString(6, user.getSummonername());
                preparedStatement.setString(7, user.getBio());
                preparedStatement.setString(8, user.getElo());
                preparedStatement.setInt(9, userId);

                int rowsUpdated = preparedStatement.executeUpdate();

                // Check if any rows were updated
                if (rowsUpdated > 0) {
                    System.out.println("User information updated successfully");
                    return true;
                } else {
                    System.out.println("Failed to update user information");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
