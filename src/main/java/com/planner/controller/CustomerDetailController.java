package main.java.com.planner.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerDetailController {

    private MainApp mainApp;
    private Customer customer;

    @FXML
    private Label customerLabel;

    @FXML
    private TextField firstNameField, lastNameField, addField, add2FIeld, phoneField, cityField, codeField;

    @FXML
    private Label firstNameError, lastNameError, addressError, phoneError, cityError, codeError;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private Button saveButton;

    private boolean isFirst, isLast, isAddress, isPhone, isCity, isCode, isCountry;

    //default constructor
    public CustomerDetailController(){}


    @FXML
    private void selectionChangedHandler(ActionEvent event){
        countryComboBox.setStyle("-fx-border-color: transparent");
        lastNameError.setVisible(false);
        isCountry = true;
        validFormCheck();
    }

    @FXML
    private void saveButtonHandler(ActionEvent event){

        //TODO:: validate input data
        mainApp.saveNewCustomer(customer);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event){
        mainApp.saveNewCustomer(null);
    }

    public void setData(MainApp mainApp, Customer customer){
        this.mainApp = mainApp;
        this.customer = customer;
        if(customer != null){
            initFields(customer);
        }else{
            customerLabel.setText("New Customer");
        }
    }

    @FXML
    public void initialize(){
        String[] locates = Locale.getISOCountries();
        List<String> countries = new ArrayList<>();
        for(String locate : locates){

            countries.add(new Locale("",locate).getDisplayName());
        }
        countryComboBox.setItems(FXCollections.observableArrayList(countries));
        saveButton.setDisable(true);

        firstNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            lastNameError.setVisible(newValue.isEmpty());
            validFormCheck();
        }));

        lastNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            lastNameError.setVisible(newValue.isEmpty());
            validFormCheck();
        }));
        addField.textProperty().addListener(((observable, oldValue, newValue) -> {
            addressError.setVisible(newValue.isEmpty());
            validFormCheck();
        }));
        phoneField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.isEmpty() || )
            phoneError.setVisible(newValue.isEmpty());
            validFormCheck();
        }));
        cityField.textProperty().addListener(((observable, oldValue, newValue) -> {
            cityError.setVisible(newValue.isEmpty());
            validFormCheck();
        }));
        codeField.textProperty().addListener(((observable, oldValue, newValue) -> {
            codeError.setVisible(newValue.isEmpty());
            validFormCheck();
        }));
    }

    private void validFormCheck(){
        boolean isValidToSave = isFirst && isLast && isAddress && isPhone && isCity && isCode && isCountry;
        saveButton.setDisable(!isValidToSave);
    }

    private void initFields(Customer customer){



    }

}
