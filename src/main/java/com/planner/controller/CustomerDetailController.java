package main.java.com.planner.controller;

import main.java.com.planner.MainApp;
import main.java.com.planner.model.Customer;

public class CustomerDetailController {

    private MainApp mainApp;
    private Customer customer;

    public void setData(MainApp mainApp, Customer customer){
        this.mainApp = mainApp;
        this.customer = customer;
    }

}
