package main.java.com.planner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.User;


public class CustomerPageController {

    private MainApp mainApp;
    private User user;

    @FXML
    private void appointmentButtonHandler(ActionEvent event) {
        mainApp.AppointmentPageLoad();
    }

    @FXML
    private void calendarButtonHandler(ActionEvent event) {
        mainApp.calendarPageLoad();
    }

    @FXML
    private void reportButtonHandler(ActionEvent actionEvent) {
        mainApp.reportPageLoad();
    }

    public void setData(MainApp mainApp, User user){
        this.mainApp = mainApp;
        this.user = user;
    }


}
