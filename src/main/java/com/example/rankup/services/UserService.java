package com.example.rankup.services;

import com.example.rankup.entities.User;
import com.example.rankup.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.time.Month;
import javafx.scene.control.Alert;
import org.mindrot.jbcrypt.BCrypt;

public class UserService implements Iservice<User>
{

    private LocalDate birthdate;
    static Connection cnx = null;

    public UserService() {
        cnx = DataSource.getInstance().getCnx();
    }

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
    public String getBlockReason(String email) {
        try {
            String query = "SELECT why_blocked FROM user WHERE email = ? AND status = 'blocked'";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("why_blocked");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No block reason found
    }

    public String loginUser(String email, String password) {
        try {
            String query = "SELECT id, password, roles, status, why_blocked FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("password");
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            // Password is correct, check user roles
                            String roles = resultSet.getString("roles");
                            int sessionValue;
                            if (roles.contains("coach") || roles.contains("player")) {
                                // User is coach or player
                                sessionValue = -1;
                            } else if (roles.contains("admin")) {
                                // User is admin
                                sessionValue = 1;
                            } else {
                                // User has no defined role
                                // You can handle this case as needed
                                return "User role not defined";
                            }

                            // Check user status
                            String status = resultSet.getString("status");
                            if ("blocked".equals(status)) {
                                String blockReason = resultSet.getString("why_blocked");
                                // User is blocked, return block reason
                                return blockReason;
                            }

                            int userId = resultSet.getInt("id");
                            SessionManager.setSession("userId", userId);
                            SessionManager.setSession("email", email);
                            SessionManager.setSession("role", sessionValue);
                            return null; // Login successful
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return "Invalid email or password"; // Login failed
    }
    public boolean changePassword(String email, String newPassword) {
        try {
            // Hash the new password
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Update the user's password in the database
            String query = "UPDATE user SET password = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, hashedPassword);
                preparedStatement.setString(2, email);

                int rowsUpdated = preparedStatement.executeUpdate();

                // Check if any rows were updated
                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully");
                    return true;
                } else {
                    System.out.println("Failed to update password");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateResetToken(String email, String resetToken) {
        try {
            String query = "UPDATE user SET reset_token = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, resetToken);
                preparedStatement.setString(2, email);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Reset token updated successfully");
                    SessionManager.setSession("resetToken", resetToken);
                    return true;
                } else {
                    System.out.println("Failed to update reset token");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getEmailByResetToken(String resetToken) {
        try {
            String query = "SELECT email FROM user WHERE reset_token = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, resetToken);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("email");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No email found for the reset token
    }

    public boolean changePassword(int userId, String newPassword) {
        try {
            // Hash the new password
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Update the user's password in the database
            String query = "UPDATE user SET password = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, hashedPassword);
                preparedStatement.setInt(2, userId);

                int rowsUpdated = preparedStatement.executeUpdate();

                // Check if any rows were updated
                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully");
                    return true;
                } else {
                    System.out.println("Failed to update password");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateUserStatusAndBlockReason(int userId, String status, String whyBlocked) {
        try {
            String query = "UPDATE user SET status = ?, why_blocked = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1,status );
                preparedStatement.setString(2, whyBlocked);
                preparedStatement.setInt(3, userId);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("User status and block reason updated successfully");
                    return true;
                } else {
                    System.out.println("Failed to update user status and block reason");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
        String query = "INSERT INTO `user`(`id`, `email`, `equipe_id`, `firstname`, " +
                "`lastname`, `reset_token`, `username`, `roles`, `password`, `photo`, " +
                "`phone`, `birthdate`, `why_blocked`, `status`, `elo`, `bio`, " +
                "`summonername`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());  // Assuming id is an integer
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getEquipe_id());
            preparedStatement.setString(4, user.getFirstname());
            preparedStatement.setString(5, user.getLastname());
            preparedStatement.setString(6, user.getResetToken());
            preparedStatement.setString(7, user.getUsername());
            preparedStatement.setString(8, String.join(",", user.getRoles())); // Assuming roles is a list of strings
            preparedStatement.setString(9, user.getPassword());
            preparedStatement.setString(10, user.getPhoto());
            preparedStatement.setString(11, user.getPhone());
            preparedStatement.setDate(12, java.sql.Date.valueOf(user.getBirthdate())); // Assuming birthdate is a LocalDate
            preparedStatement.setString(13, user.getWhyBlocked());
            preparedStatement.setString(14, user.getStatus());
            preparedStatement.setString(15, user.getElo());
            preparedStatement.setString(16, user.getBio());
            preparedStatement.setString(17, user.getSummonername());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void modifier(User user) {

    }

    public void modifierEquipe_id(User user) {
        String query = "UPDATE user SET equipe_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, user.getEquipe_id());
            preparedStatement.setInt(2, user.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        try {
            String query = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setEmail(resultSet.getString("email"));
                        user.setFirstname(resultSet.getString("firstname"));
                        user.setLastname(resultSet.getString("lastname"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPhone(resultSet.getString("phone"));
//                        user.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
                        user.setBirthdate(LocalDate.of(1, Month.JANUARY, 1));
                        user.setElo(resultSet.getString("elo"));
                        user.setBio(resultSet.getString("bio"));
                        user.setSummonername(resultSet.getString("summonername"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
        return false;
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String query = "SELECT * FROM user";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setEmail(resultSet.getString("email"));
                        user.setFirstname(resultSet.getString("firstname"));
                        user.setLastname(resultSet.getString("lastname"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPhone(resultSet.getString("phone"));

//                        user.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
                        user.setBirthdate(LocalDate.of(1, Month.JANUARY, 1));

                        user.setElo(resultSet.getString("elo"));
                        user.setBio(resultSet.getString("bio"));
                        user.setSummonername(resultSet.getString("summonername"));
                        userList.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

}
