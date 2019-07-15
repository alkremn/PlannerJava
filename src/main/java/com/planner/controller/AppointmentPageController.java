package main.java.com.planner.controller;

import main.java.com.planner.MainApp;
import main.java.com.planner.model.User;

public class AppointmentPageController {

    private MainApp mainApp;
    private User user;

    public void setData(MainApp mainApp, User user){
        this.mainApp = mainApp;
        this.user = user;
    }


}
