package main.java.com.planner.controller.Customer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Address;
import main.java.com.planner.model.City;
import main.java.com.planner.model.Country;
import main.java.com.planner.model.Customer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerDetailController {

    private MainApp mainApp;
    private Customer customer;
    private String user;
    private boolean isExisting;

    @FXML
    private Label customerLabel;

    @FXML
    private RadioButton notActiveButton;

    @FXML
    private TextField firstNameField, lastNameField, addField, add2Field, phoneField, cityField, codeField;

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
        if(customer != null)
            mainApp.saveCustomer(createCustomer(isExisting), isExisting);
        else
            mainApp.saveCustomer(createCustomer(isExisting), isExisting);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event){
        mainApp.saveCustomer(null, !isExisting);
    }

    public void setData(MainApp mainApp, Customer customer, String user){
        this.mainApp = mainApp;
        this.customer = customer;
        this.user = user;
        if(customer != null){
            initFields(customer);
            isExisting = true;
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

        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            isFirstValid = !newValue.isEmpty();
            firstNameError.setVisible(!isFirstValid);
            validFormCheck();
        });

        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            isLastValid = !newValue.isEmpty();
            lastNameError.setVisible(!isLastValid);
            validFormCheck();
        });
        addField.textProperty().addListener((observable, oldValue, newValue) -> {
            isAddressValid = !newValue.isEmpty();
            addressError.setVisible(!isAddressValid);
            validFormCheck();
        });
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isNumber = true;
            try{
                Long.parseLong(newValue);
            }catch (NumberFormatException e){
                isNumber = false;
            }
            isPhoneValid = !newValue.isEmpty() && isNumber;
            phoneError.setVisible(!isPhoneValid);
            validFormCheck();
        });
        cityField.textProperty().addListener((observable, oldValue, newValue) -> {
            isCityValid = !newValue.isEmpty();
            cityError.setVisible(!isCityValid);
            validFormCheck();
        });
        codeField.textProperty().addListener((observable, oldValue, newValue) -> {
            isCodeValid = !newValue.isEmpty();
            codeError.setVisible(!isCodeValid);
            validFormCheck();
        });
    }

    private void validFormCheck(){
        boolean isValidToSave = isFirstValid && isLastValid && isAddressValid && isPhoneValid && isCityValid
                && isCodeValid && isCountryValid;
        saveButton.setDisable(!isValidToSave);
    }

    private void initFields(Customer customer){
        customerLabel.setText("Modify Customer");
        notActiveButton.setSelected(!customer.isActive());
        String[] firstLast = customer.getName().split(" ");
        firstNameField.setText(firstLast[0]);
        lastNameField.setText(firstLast[1]);
        addField.setText(customer.getAddress().getAddress());
        add2Field.setText(customer.getAddress().getAddress2());
        phoneField.setText(customer.getAddress().getPhone());
        cityField.setText(customer.getAddress().getCity().getName());
        codeField.setText(customer.getAddress().getPostalCode());
        countryComboBox.getSelectionModel().select(customer.getAddress().getCity().getCountry().getName());
        isCountryValid = true;
        saveButton.setDisable(false);
    }

    private Customer createCustomer(boolean isExisting) {
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("UTC"));
        if (!isExisting) {
            Country country = new Country(0, countryComboBox.getSelectionModel().getSelectedItem(), currentDate,
                    user, currentDate, user);
            City city = new City(0, cityField.getText(), country, currentDate, user, currentDate, user);
            Address address = new Address(0, addField.getText(), add2Field.getText(), city, codeField.getText(),
                    phoneField.getText(), currentDate, user, currentDate, user);
            String fullName = firstNameField.getText() + " " + lastNameField.getText();
            boolean isActive = !notActiveButton.isSelected();
            return new Customer(0, fullName, address, isActive, currentDate, user, currentDate, user);
        } else {
            Address address = customer.getAddress();
            City city = address.getCity();
            Country country = city.getCountry();
            country.setName(countryComboBox.getSelectionModel().getSelectedItem());
            country.setLastUpdate(currentDate);
            country.setLastUpdateBy(user);
            city.setName(cityField.getText());
            city.setLastUpdate(currentDate);
            city.setLastUpdateBy(user);
            address.setAddress(addField.getText());
            address.setAddress2(add2Field.getText());
            address.setPostalCode(codeField.getText());
            address.setPhone(phoneField.getText());
            address.setLastUpdate(currentDate);
            address.setLastUpdateBy(user);

            String fullName = firstNameField.getText() + " " + lastNameField.getText();
            customer.setName(fullName);
            customer.setActive(!notActiveButton.isSelected());
            customer.setLastUpdate(currentDate);
            customer.setLastUpdateBy(user);
            return customer;
        }
    }
}
