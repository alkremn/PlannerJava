package main.java.com.planner.controller.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Day;
import main.java.com.planner.model.User;

import java.time.LocalDate;

public class CalendarPageController {

    private final int YEAR_PERIOD = 10;
    private MainApp mainApp;
    private Button activeDayButton;
    private User user;
    private ObservableList<Day> days = FXCollections.observableArrayList();
    private ObservableList<Day> currentWeek = FXCollections.observableArrayList();
    private ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March",
            "April", "May", "June", "July", "August", "September", "October", "November", "December");
    private ObservableList<String> years = FXCollections.observableArrayList();

    @FXML
    private Label usernameLabel;

    @FXML
    private ComboBox<String> monthBox, yearBox;

    @FXML
    private FlowPane dayPane;

    @FXML
    private Button showButton;

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
        this.monthBox.setItems(months);
        this.yearBox.setItems(years);
        LocalDate date = LocalDate.now();
        monthBox.getSelectionModel().select(date.getMonth().getValue() - 1);
        yearBox.getSelectionModel().select(String.valueOf(date.getYear()));
        buildYears(LocalDate.now());
        createCalendar();
    }

    @FXML
    private void showButtonHandler(ActionEvent event){
        int intMonth = monthBox.getSelectionModel().getSelectedIndex() + 1;
        String year = yearBox.getSelectionModel().getSelectedItem();
        if(intMonth > 0 && year != null){
            int intYear = Integer.parseInt(year);
            BuildCalendar(LocalDate.of(intYear, intMonth, 1));
            createCalendar();
        }
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
    }

    private void BuildCalendar(LocalDate targetDate) {
        days.clear();

        LocalDate d = LocalDate.of(targetDate.getYear(), targetDate.getMonth(), 1);
        int offset = d.getDayOfWeek().getValue();
        if (offset != 6) d = d.plusDays(-offset);

        for(int i = 1; i < 42; i++){
            Day day = new Day(d, (targetDate.getMonth() == d.getMonth()), (d.equals(LocalDate.now())));
            days.add(day);
            d = d.plusDays(1);
        }
    }

    private void createCalendar(){
        dayPane.getChildren().clear();
        for(Day d : days){
            Button button = new Button(String.valueOf(d.getDate().getDayOfMonth()));
            button.getStyleClass().add("calendarDayButton");
            button.onActionProperty().setValue(this::dayButtonHandler);
            if(d.isTargetMonth()){
                button.setStyle("-fx-text-fill: #787878");
                button.setId("targetMonth");
            }
            else{
                button.setDisable(true);
            }
            if(d.isToday()){
                button.setStyle("-fx-background-color: #458fbb");
                button.setId(button.getId() + "today");
            }
            dayPane.getChildren().add(button);

        }
    }
    private void dayButtonHandler(ActionEvent event){
        if(activeDayButton != null){
            activeDayButton.setStyle("-fx-background-color: transparent");
            if(activeDayButton.getId() != null){
                if(activeDayButton.getId().equals("targetMonth")){
                    activeDayButton.setStyle("-fx-text-fill: #787878");
                }else{
                    activeDayButton.setStyle("-fx-background-color: #458fbb");
                }
            }
        }
        activeDayButton = (Button)event.getSource();
        activeDayButton.setStyle("-fx-background-color: #787878;");
        System.out.println(activeDayButton.getText());
    }

    private void buildYears(LocalDate targetDate){
        int lowerBound = targetDate.getYear() - YEAR_PERIOD;
        int upperBound = targetDate.getYear() + YEAR_PERIOD;
        for(int year = lowerBound; year < upperBound; year++){
            years.add(String.valueOf(year));
        }
    }

}
