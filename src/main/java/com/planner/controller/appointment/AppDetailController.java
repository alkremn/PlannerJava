package main.java.com.planner.controller.appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.*;

public class AppDetailController {

    private MainApp mainApp;
    private Customer customer;
    private Appointment appointment;
    private boolean isExisting;

    @FXML
    private Label appointmentLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private TextField titleField, descField, locationField, contactField;

    @FXML
    private Label titleError, descError, locationError, contactError, typeError, dateError, startError, endError;

    @FXML
    private ComboBox<String> typePicker, startTimePicker, endTimePicker;

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
        if(appointment != null) {
            initFields(customer);
            isExisting = true;
        }
    }

    @FXML
    public void initialize(){
        typePicker.setItems(FXCollections.observableArrayList( "New Appt", "Follow up"));
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
       return null;
    }
}
