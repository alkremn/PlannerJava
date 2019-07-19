package main.java.com.planner.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private RadioButton activeButton, notActiveButton;

    @FXML
    private TextField firstNameField, lastNameField, addField, add2FIeld, phoneField, cityField, codeField;

    @FXML
    private Label firstNameError, lastNameError, addressError, phoneError, cityError, codeError;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private Button saveButton;

    private boolean isFirstValid, isLastValid, isAddressValid, isPhoneValid, isCityValid, isCodeValid, isCountryValid;

    //default constructor
    public CustomerDetailController(){}


    @FXML
    private void selectionChangedHandler(ActionEvent event){
        isCountryValid = true;
        validFormCheck();
    }

    @FXML
    private void saveButtonHandler(ActionEvent event){
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
            isFirstValid = !newValue.isEmpty();
            firstNameError.setVisible(!isFirstValid);
            validFormCheck();
        }));

        lastNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            isLastValid = !newValue.isEmpty();
            lastNameError.setVisible(!isLastValid);
            validFormCheck();
        }));
        addField.textProperty().addListener(((observable, oldValue, newValue) -> {
            isAddressValid = !newValue.isEmpty();
            addressError.setVisible(!isAddressValid);
            validFormCheck();
        }));
        phoneField.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean isNumber = true;
            try{
            Long.parseLong(newValue);
            }catch (NumberFormatException e){
                isNumber = false;
            }
            isPhoneValid = !newValue.isEmpty() && isNumber;
            phoneError.setVisible(!isPhoneValid);
            validFormCheck();
        }));
        cityField.textProperty().addListener(((observable, oldValue, newValue) -> {
            isCityValid = !newValue.isEmpty();
            cityError.setVisible(!isCityValid);
            validFormCheck();
        }));
        codeField.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean isNumber = true;
            try{
                Long.parseLong(newValue);
            }catch (NumberFormatException e){
                isNumber = false;
            }
            isCodeValid = !newValue.isEmpty() && isNumber;
            codeError.setVisible(!isCodeValid);
            validFormCheck();
        }));
    }

    private void validFormCheck(){
        boolean isValidToSave = isFirstValid && isLastValid && isAddressValid && isPhoneValid && isCityValid
                && isCodeValid && isCountryValid;
        saveButton.setDisable(!isValidToSave);
    }

    private void initFields(Customer customer){
        notActiveButton.setSelected(!customer.isActive());
        String[] firstLast = customer.getName().split(" ");
        firstNameField.setText(firstLast[0]);
        lastNameField.setText(firstLast[1]);
        addField.setText(customer.getAddress().getAddress());
        add2FIeld.setText(customer.getAddress().getAddress2());
        phoneField.setText(customer.getAddress().getPhone());
        cityField.setText(customer.getAddress().getCity().getName());
        codeField.setText(customer.getAddress().getPostalCode());
        countryComboBox.getSelectionModel().select(customer.getAddress().getCity().getCountry().getName());
    }

}
