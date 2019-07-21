package main.java.com.planner.controller;

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
    private TextField firstNameField, lastNameField, addField, add2Field, phoneField, cityField, codeField;

    @FXML
    private Label firstNameError, lastNameError, addressError, phoneError, cityError, codeError;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private Button saveButton;

    private boolean isFirstValid, isLastValid, isAddressValid, isPhoneValid, isCityValid, isCodeValid, isCountryValid;

    @FXML
    private ComboBox<String> typeComboBox;

    //default constructor
    public AppDetailController(){}


    @FXML
    private void selectionChangedHandler(ActionEvent event){
        isCountryValid = true;
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
        if(customer != null) {
            initFields(customer);
            isExisting = true;
        }
    }

    @FXML
    public void initialize(){
        typeComboBox.setItems(FXCollections.observableArrayList( "New Appt", "Follow up"));
    }

    private void validFormCheck(){
        boolean isValidToSave = isFirstValid && isLastValid && isAddressValid && isPhoneValid && isCityValid
                && isCodeValid && isCountryValid;
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
