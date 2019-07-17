package main.java.com.planner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Customer;


public class CustomerDetailController {

    private MainApp mainApp;
    private Customer customer;

    @FXML
    private Label customerLabel;

    //default constructor
    public CustomerDetailController(){}


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

    private void initFields(Customer customer){

    }

}
