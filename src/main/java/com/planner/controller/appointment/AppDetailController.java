package main.java.com.planner.controller.appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.*;

import java.sql.Time;
import java.time.*;
import java.util.Calendar;
import java.util.List;

public class AppDetailController {

    private MainApp mainApp;
    private Customer customer;
    private Appointment appointment;
    private User user;
    private boolean isExisting;
    private final int START_TIME_HOUR = 8;
    private final int END_TIME_HOUR = 17;
    private final int MINUTES_IN_HOUR = 60;
    private final int TIME_PACE = 15;

    @FXML
    private Label appointmentLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descTextArea, locationTextArea, contactTextArea, urlTextArea;

    @FXML
    private Label titleError, descError, locationError, contactError, urlError;

    @FXML
    private ComboBox<String> typePicker;

    @FXML
    private ComboBox<TimeSpan> startTimePicker, endTimePicker;

    @FXML
    private DatePicker appDatePicker;

    @FXML
    private Button saveButton;

    private boolean isTitleValid, isDescValid, isLocationValid, isContactValid, isUrlValid, isTypeValid, isDateValid,
            isStartValid, isEndValid;

    //default constructor
    public AppDetailController(){}


    @FXML
    private void startSelectionChangedHandler(ActionEvent event){
        isStartValid = true;
        validFormCheck();
    }

    @FXML
    private void endSelectionChangedHandler(ActionEvent event){
        isEndValid = true;
        validFormCheck();
    }

    @FXML
    private void typeSelectionChangedHandler(ActionEvent event){
        isTypeValid = true;
        validFormCheck();
    }

    @FXML
    private void appDateSelected(ActionEvent event){
        System.out.println("Date selected");
        isDateValid = true;
        validFormCheck();
    }

    @FXML
    private void saveButtonHandler(ActionEvent event){
        if(customer != null)
            mainApp.saveAppointment(createAppointment(isExisting), isExisting);
        else
            mainApp.saveAppointment(createAppointment(isExisting), isExisting);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event){
        mainApp.saveCustomer(null, !isExisting);
    }

    public void setData(MainApp mainApp, Appointment appointment, Customer customer, User user){
        this.mainApp = mainApp;
        this.appointment = appointment;
        this.customer = customer;
        this.user = user;
        startTimePicker.setItems(createTime(new TimeSpan(START_TIME_HOUR,0),FXCollections.observableArrayList()));
        endTimePicker.setItems(createTime(new TimeSpan(START_TIME_HOUR,0),FXCollections.observableArrayList()));
        if(appointment != null) {
            initFields(customer);
            saveButton.setDisable(false);
            isExisting = true;
        }
    }

    @FXML
    public void initialize(){
        typePicker.setItems(FXCollections.observableArrayList( "New Appt", "Follow up"));
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            isTitleValid = !newValue.isEmpty();
            titleError.setVisible(!isTitleValid);
            validFormCheck();
        });

        descTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            isDescValid = !newValue.isEmpty();
            descError.setVisible(!isDescValid);
            validFormCheck();
        });

        locationTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            isLocationValid = !newValue.isEmpty();
            locationError.setVisible(!isLocationValid);
            validFormCheck();
        });

        contactTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            isContactValid = !newValue.isEmpty();
            contactError.setVisible(!isContactValid);
            validFormCheck();
        });

        urlTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            isUrlValid = !newValue.isEmpty();
            urlError.setVisible(!isUrlValid);
            validFormCheck();
        });

    }

    private void validFormCheck(){
        boolean isValidToSave = isTitleValid && isDescValid && isLocationValid && isContactValid && isUrlValid &&
                isTypeValid && isDateValid && isStartValid && isEndValid;
        saveButton.setDisable(!isValidToSave);
    }

    private void initFields(Customer customer){
        appointmentLabel.setText("Modify Appointment");
        customerNameLabel.setText(customer.getName());
    }

    private Appointment createAppointment(boolean isExisting){
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("UTC"));
        Appointment appointment = null;
        if (!isExisting) {
            int customerId = customer.getCustomerId();
            int userId = user.getUserId();
            String title = titleField.getText();
            String desc = descTextArea.getText();
            String location = locationTextArea.getText();
            String contact = contactTextArea.getText();
            String url = urlTextArea.getText();
            String type = typePicker.getSelectionModel().getSelectedItem();
            LocalDate date = appDatePicker.getValue();
            TimeSpan start = startTimePicker.getValue();
            TimeSpan end = endTimePicker.getValue();

            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(start.hours,start.minutes, 0));
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.of(end.hours, end.minutes, 0));
            appointment = new Appointment(0,customerId,userId,title,desc,location,contact,type,url,startTime,endTime,currentDate,
                    user.getUserName(), currentDate, user.getUserName());

        } else {

        }
        return appointment;
    }

    private ObservableList<TimeSpan> createTime(TimeSpan startTime, ObservableList<TimeSpan> timeCollection){
        timeCollection.clear();
        int mins = startTime.minutes;
        for(int hour = startTime.hours; hour < END_TIME_HOUR; hour++){
            for(int min = mins; min < MINUTES_IN_HOUR; min += TIME_PACE){
                timeCollection.add(new TimeSpan(hour, min));
            }
            mins = 0;
        }
        return timeCollection;
    }
}