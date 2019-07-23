package main.java.com.planner.controller.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Day;
import main.java.com.planner.model.User;

import java.time.LocalDate;

public class CalendarPageController {

    private MainApp mainApp;
    private User user;
    private ObservableList<Day> days = FXCollections.observableArrayList();
    private ObservableList<Day> currentWeek = FXCollections.observableArrayList();
    private ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March",
            "April", "May", "June", "July", "August", "September", "October", "November", "December");

    @FXML
    private Label usernameLabel;



    @FXML
    private Pane dayPane;

    @FXML
    private void initialize(){
        BuildCurrentCalendarMonth();
    }

    @FXML
    private void customerButtonHandler(ActionEvent event){
        mainApp.customersPageLoad();
    }

    @FXML
    private void appointmentButtonHandler(ActionEvent event) {
        mainApp.appointmentPageLoad();
    }

    @FXML
    private void calendarButtonHandler(ActionEvent event) {
        mainApp.calendarPageLoad();
    }

    @FXML
    private void reportButtonHandler(ActionEvent actionEvent) {
        mainApp.reportPageLoad();
    }

    public void setData(MainApp mainApp, User user){
        this.mainApp = mainApp;
        this.user = user;
        this.usernameLabel.setText(user.getUserName());
        dayPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Pane pane = (Pane)e.getSource();
            System.out.println(pane.getId());
        });
    }

    @FXML
    private void showMonthHandler(ActionEvent event){
        //TODO::
    }

    @FXML
    private void showDaysHandler(ActionEvent event){
        //TODO::
    }

    @FXML
    private void monthHandler(ActionEvent event){
        //TODO::
    }

    @FXML
    private void weekHandler(ActionEvent event){
        //TODO::
    }

    private void BuildCurrentCalendarMonth(){
        BuildCalendar(LocalDate.now());
        System.out.println("");
    }

    private void BuildCalendar(LocalDate targetDate) {
        days.clear();

        LocalDate d = LocalDate.of(targetDate.getYear(), targetDate.getMonth(), 1);
        int offset = d.getDayOfWeek().getValue();
        if (offset != 1) d = d.plusDays(-offset);

        for(int i = 1; i < 42; i++){
            Day day = new Day(d, (targetDate.getMonth() == d.getMonth()), (d.equals(LocalDate.now())));
            days.add(day);
            d = d.plusDays(1);
        }
    }


}
