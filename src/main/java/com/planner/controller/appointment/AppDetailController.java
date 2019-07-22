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
    private TextField titleField, descField, locationField, contactField, urlField;

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

    private boolean isTitleValid, isDescValid, isLocationValid, isContactValid, isTypeValid, isDateValid,
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

    public void setData(MainApp mainApp, Appointment appointment, Customer customer){
        this.mainApp = mainApp;
        this.appointment = appointment;
        this.customer = customer;
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
        titleField.textProperty().addListener(((observable, oldValue, newValue) -> {
            isTitleValid = !newValue.isEmpty();
            titleError.setVisible(!isTitleValid);
            validFormCheck();
        }));

        descField.textProperty().addListener(((observable, oldValue, newValue) -> {
            isDescValid = !newValue.isEmpty();
            descError.setVisible(!isDescValid);
            validFormCheck();
        }));

        locationField.textProperty().addListener(((observable, oldValue, newValue) -> {
            isLocationValid = !newValue.isEmpty();
            locationError.setVisible(!isLocationValid);
            validFormCheck();
        }));

        contactField.textProperty().addListener(((observable, oldValue, newValue) -> {
            isContactValid = !newValue.isEmpty();
            contactError.setVisible(!isContactValid);
            validFormCheck();
        }));

    }

    private void validFormCheck(){
        boolean isValidToSave = isTitleValid && isDescValid && isLocationValid && isContactValid && isTypeValid && isDateValid
                && isStartValid && isEndValid;
        saveButton.setDisable(!isValidToSave);
    }

    private void initFields(Customer customer){
        appointmentLabel.setText("Modify Appointment");
        customerNameLabel.setText(customer.getName());
    }

    private Appointment createAppointment(boolean isExisting){
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("UTC"));
        if (!isExisting) {
            String title = titleField.getText();
            String desc = descField.getText();
            String location = locationField.getText();
            String contact = contactField.getText();
            String type = typePicker.getSelectionModel().getSelectedItem();
            LocalDate date = appDatePicker.getValue();
            TimeSpan startTime = startTimePicker.getValue();
            TimeSpan endTime = endTimePicker.getValue();



        } else {

        }
        return null;
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
