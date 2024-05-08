package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Calendar implements Initializable {

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    private final EventService eventService = new EventService();

    private ZonedDateTime dateFocus;

    public Calendar() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));
        calendar.getChildren().clear();

        int daysInMonth = dateFocus.getMonth().length(dateFocus.toLocalDate().isLeapYear());
        LocalDate firstDayOfMonth = LocalDate.of(dateFocus.getYear(), dateFocus.getMonth(), 1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1: Monday, 7: Sunday

        for (int i = 1; i <= daysInMonth + dayOfWeek - 1; i++) {
            StackPane stackPane = new StackPane();
            Rectangle rectangle = new Rectangle(80, 80); // Adjust size as needed
            rectangle.setFill(javafx.scene.paint.Color.LIGHTGREY); // Set rectangle color

            int currentDay = i - dayOfWeek + 1;
            if (currentDay > 0 && currentDay <= daysInMonth) {
                Text dateText = new Text(String.valueOf(currentDay));
                dateText.setFill(javafx.scene.paint.Color.WHITE); // Set text color
                stackPane.getChildren().addAll(rectangle, dateText);

                // Fetch events for the current day
                LocalDate currentDate = LocalDate.of(dateFocus.getYear(), dateFocus.getMonth(), currentDay);
                List<Event> events = eventService.getEventsByDate(currentDate);

                // Display the day and events in a vertical stack
                VBox vbox = new VBox();
                vbox.getChildren().add(dateText);
                if (!events.isEmpty()) {
                    // Show events below the day
                    for (Event event : events) {
                        Text eventText = new Text(event.getNom_event());
                        vbox.getChildren().add(eventText);
                    }
                }
                stackPane.getChildren().add(vbox);

                // Tooltip for events
                if (!events.isEmpty()) {
                    String eventDetails = events.stream()
                            .map(event -> event.getNom_event() + " \n Type: " + event.getType() /*+ ", End Date: " + event.getDate_fin()*/)
                            .collect(Collectors.joining("\n"));
                    String endDate = events.get(0).getDate_fin();

                    stackPane.setOnMouseEntered(e -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Events for " + currentDate + " End: " + endDate);
                        alert.setHeaderText(null);
                        alert.setContentText(eventDetails);
                        alert.show();
                    });
                }

            }

            calendar.getChildren().add(stackPane);
        }
    }

    public void BackButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherSponsor.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
