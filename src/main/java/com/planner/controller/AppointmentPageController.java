package main.java.com.planner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Appointment;
import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.time.ZonedDateTime;

public class AppointmentPageController {

    private MainApp mainApp;
    private User user;

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, Boolean> statusColumn;

    @FXML
    private TableColumn<Customer, ZonedDateTime> createDateColumn;


    @FXML
    private TableView<Appointment> appointmentTableView;



    @FXML
    void initialize() {
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        customerNameColumn.setCellValueFactory((cellData -> cellData.getValue().nameProperty()));
        statusColumn.setCellValueFactory((cellData -> cellData.getValue().activeProperty()));
        createDateColumn.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());

        customerIdColumn.setStyle("-fx-alignment: CENTER;");
        customerNameColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setStyle("-fx-alignment: CENTER;");
        createDateColumn.setStyle("-fx-alignment: CENTER;");
    }

    @FXML
    private void addAppointmentHandler(ActionEvent event){
        Customer selectedCustomer= customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null)
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        else
            mainApp.appDetailPageLoad(null, selectedCustomer);
    }

    @FXML
    private void modifyAppointmentHander(ActionEvent event){
        //TODO::
    }

    @FXML
    private void deleteAppointmentHandler(ActionEvent event){

    }

    @FXML
    private void customerButtonHandler(ActionEvent event){
        mainApp.customersPageLoad();
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
        this.usernameLabel.setText(user.getUserName());
        customerTableView.setItems(mainApp.customerList);
    }


}
