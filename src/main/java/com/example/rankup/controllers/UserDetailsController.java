    package com.example.rankup.controllers;

    import com.example.rankup.entities.User;
    import com.example.rankup.services.SessionManager;
    import com.example.rankup.services.UserService;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;

    import java.io.IOException;

    public class UserDetailsController {

        @FXML
        private Text nameq;
        @FXML
        private Text idq;

        @FXML
        private Text lastnameq;

        @FXML
        private Text phoneq;

        @FXML
        private Text birthdayq;

        @FXML
        private Text eloq;


        @FXML
        private Text bioq;

        @FXML
        private Text sumnameq;
        private int userId;
        public void setUserId(int userId) {
            this.userId = userId;
        }
        private UserService userService;

        @FXML
        public void initialize() {
            userService = new UserService();
            System.out.println("User ID: " + userId);
        }

        public void populateUserDetails(int userId) {
            User user = userService.getOneByID(userId);
            if (user != null) {
                idq.setText(String.valueOf(user.getId()));
                nameq.setText(user.getFirstname());
                lastnameq.setText(user.getLastname());
                phoneq.setText(user.getPhone());
                birthdayq.setText(user.getBirthdate().toString());
                eloq.setText(user.getElo());
                bioq.setText(user.getBio());
                sumnameq.setText(user.getSummonername());
            } else {
                nameq.setText("N/A");
                lastnameq.setText("N/A");
                phoneq.setText("N/A");
                birthdayq.setText("N/A");
                eloq.setText("N/A");
                bioq.setText("N/A");
                sumnameq.setText("N/A");
            }
        }
        public void setUserService(UserService userService) {
            this.userService = userService;
        }

        @FXML
        private void logout() {

            SessionManager.clearSession();


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Login");

                Stage currentStage = (Stage) lastnameq.getScene().getWindow();
                currentStage.close();

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @FXML
        private void navigateToDialog() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminblockuser.fxml"));
                Parent root = loader.load();

                UserblockController controller = loader.getController();
                int userIdFromText = Integer.parseInt(idq.getText());
                controller.setUserId(userIdFromText);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Block User");

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage currentStage = (Stage) nameq.getScene().getWindow();
            currentStage.close();
        }




    }
