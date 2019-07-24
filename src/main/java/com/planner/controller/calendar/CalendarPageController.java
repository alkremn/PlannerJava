package main.java.com.planner.controller.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Appointment;
import main.java.com.planner.model.Day;
import main.java.com.planner.model.User;
import sun.java2d.cmm.lcms.LcmsServiceProvider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CalendarPageController {

    private final int YEAR_PERIOD = 10;
    private MainApp mainApp;
    private Button activeDayButton;
    private ObservableList<Day> days = FXCollections.observableArrayList();
    private ObservableList<Appointment> currentAppointments = FXCollections.observableArrayList();
    private ObservableList<Day> currentWeek = FXCollections.observableArrayList();
    private ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March",
            "April", "May", "June", "July", "August", "September", "October", "November", "December");
    private ObservableList<String> years = FXCollections.observableArrayList();

    @FXML
    private Label usernameLabel, selectedMonth, selectedDate;

    @FXML
    private ComboBox<String> monthBox, yearBox;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> cusNameColumn;


    @FXML
    private TableColumn<Appointment, String> appStartEndTimeColumn;

    @FXML
    private TableColumn<Appointment, String> appDateColumn;

    @FXML
    private FlowPane dayPane;

    @FXML
    private void initialize(){
        cusNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        appStartEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startEndTimeProperty());
        appDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateStringProperty());

        cusNameColumn.setStyle( "-fx-alignment: CENTER;");
        appStartEndTimeColumn.setStyle( "-fx-alignment: CENTER;");
        appDateColumn.setStyle( "-fx-alignment: CENTER;");

        BuildCurrentCalendarMonth();
        appointmentTableView.setItems(currentAppointments);
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
    private void reportButtonHandler(ActionEvent actionEvent) {
        mainApp.reportPageLoad();
    }

    public void setData(MainApp mainApp, User user, Future<List<Appointment>> result){
        this.mainApp = mainApp;
        this.usernameLabel.setText(user.getUserName());
        this.monthBox.setItems(months);
        this.yearBox.setItems(years);
        LocalDate date = LocalDate.now();
        monthBox.getSelectionModel().select(date.getMonth().getValue() - 1);
        yearBox.getSelectionModel().select(String.valueOf(date.getYear()));
        buildYears(LocalDate.now());
        selectedMonth.setText("This Month");
        loadAppointmentByMonth(LocalDate.now());
        setSelectedDateLabel(LocalDate.now());
    }

    @FXML
    private void showButtonHandler(ActionEvent event){
        int intMonth = monthBox.getSelectionModel().getSelectedIndex() + 1;
        String year = yearBox.getSelectionModel().getSelectedItem();
        if(intMonth > 0 && year != null){
            int intYear = Integer.parseInt(year);
            LocalDate selectedDate = LocalDate.of(intYear, intMonth, 1);
            BuildCalendar(selectedDate);
            setSelectedDateLabel(selectedDate);
            loadAppointmentByMonth(selectedDate);
        }
    }

    @FXML
    private void monthHandler(ActionEvent event){
        selectedMonth.setText("This month");
        LocalDate date = LocalDate.now();
        monthBox.getSelectionModel().select(date.getMonth().getValue() - 1);
        yearBox.getSelectionModel().select(String.valueOf(date.getYear()));
        BuildCurrentCalendarMonth();
        loadAppointmentByMonth(LocalDate.now());
    }

    @FXML
    private void weekHandler(ActionEvent event){
        selectedMonth.setText("This week");
        LocalDate date = LocalDate.now();
        monthBox.getSelectionModel().select(date.getMonth().getValue() - 1);
        yearBox.getSelectionModel().select(String.valueOf(date.getYear()));
        BuildCurrentCalendarMonth();
        loadAppointmentByWeek();
    }

    private void BuildCurrentCalendarMonth(){
        BuildCalendar(LocalDate.now());
    }

    private void BuildCalendar(LocalDate targetDate) {
        days.clear();

        LocalDate d = LocalDate.of(targetDate.getYear(), targetDate.getMonth(), 1);
        int offset = d.getDayOfWeek().getValue();
        if (offset != 7) d = d.plusDays(-offset);

        for(int i = 1; i < 42; i++){
            Day day = new Day(d, (targetDate.getMonth() == d.getMonth()), (d.equals(LocalDate.now())));
            days.add(day);
            d = d.plusDays(1);
        }
        createCalendar();
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
        int day = Integer.parseInt(activeDayButton.getText());
        int month = monthBox.getSelectionModel().getSelectedIndex() + 1;
        int year = Integer.parseInt(yearBox.getSelectionModel().getSelectedItem());
        LocalDate date = LocalDate.of(year,month,day);
        loadAppointmentByDay(date);
        setSelectedDateLabel(date);
    }

    private void buildYears(LocalDate targetDate){
        int lowerBound = targetDate.getYear() - YEAR_PERIOD;
        int upperBound = targetDate.getYear() + YEAR_PERIOD;
        for(int year = lowerBound; year < upperBound; year++){
            years.add(String.valueOf(year));
        }
    }

    private void setSelectedDateLabel(LocalDate targetDate){
        String date = String.format("%s, %s", months.get(targetDate.getMonth().getValue() - 1), targetDate.getYear());
        selectedDate.setText(date);
    }

    private void loadAppointmentByDay(LocalDate selectedDay){
        currentAppointments.clear();
        List<Appointment> selectedApp = mainApp.appointmentList.stream()
                .filter(a -> a.getStart().toLocalDate().equals(selectedDay)).collect(Collectors.toList());
        currentAppointments.addAll(selectedApp);
    }
    private void loadAppointmentByMonth(LocalDate selectedMonth){
        currentAppointments.clear();
        List<Appointment> selectedApp = mainApp.appointmentList.stream()
                .filter(a -> a.getStart().toLocalDate().getMonth().equals(selectedMonth.getMonth()) &&
                        a.getStart().toLocalDate().getYear() == selectedMonth.getYear()).collect(Collectors.toList());
        currentAppointments.addAll(selectedApp);
    }

    private void loadAppointmentByWeek(){
        LocalDate currentWeeDay  = LocalDate.now().plusDays(-1 * LocalDate.now().getDayOfWeek().getValue());
        List<LocalDate> week = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            week.add(currentWeeDay.plusDays(i));
        }
        currentAppointments.clear();

        for(LocalDate day : week){
            List<Appointment> selectedApp = mainApp.appointmentList.stream()
                    .filter(a -> a.getStart().toLocalDate().equals(day)).collect(Collectors.toList());
            currentAppointments.addAll(selectedApp);
        }
    }
}
