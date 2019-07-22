package main.java.com.planner.controller.appointment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Appointment;
import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


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
    private TableColumn<Customer, String> createDateColumn;


    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> appIdColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appStartEndTimeColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appDateColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appCreateDateColumn;


    @FXML
    void initialize() {
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        customerNameColumn.setCellValueFactory((cellData -> cellData.getValue().nameProperty()));
        statusColumn.setCellValueFactory((cellData -> cellData.getValue().activeProperty()));
        createDateColumn.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());

        appIdColumn.setCellValueFactory(cellDate -> cellDate.getValue().idProperty().asObject());
        appStartEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startEndTimeProperty());
        appDateColumn.setCellValueFactory(cellData -> cellData.getValue().startEndTimeProperty());
        appCreateDateColumn.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());

        customerIdColumn.setStyle("-fx-alignment: CENTER;");
        customerNameColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setStyle("-fx-alignment: CENTER;");
        createDateColumn.setStyle("-fx-alignment: CENTER;");

        appIdColumn.setStyle("-fx-alignment: CENTER;");
        appStartEndTimeColumn.setStyle("-fx-alignment: CENTER;");
        appDateColumn.setStyle("-fx-alignment: CENTER;");
        appCreateDateColumn.setStyle("-fx-alignment: CENTER;");
    }

    @FXML
    private void addAppointmentHandler(ActionEvent event){
        Customer selectedCustomer= customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null)
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        else
            mainApp.appDetailPageLoad(null, selectedCustomer, user);
    }

    @FXML
    private void modifyAppointmentHandler(ActionEvent event){
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        Appointment selectedApp= appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null)
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        else if (selectedApp == null)
            mainApp.showAlertMessage("No appointment selected", "please, select the appointment in the table");
        else
            mainApp.appDetailPageLoad(selectedApp, selectedCustomer, user);
    }

    @FXML
    private void deleteAppointmentHandler(ActionEvent event){
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        Appointment selectedApp = appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        } else if (selectedApp == null){
            mainApp.showAlertMessage("No appointment selected", "please, select the appointment in the table");
        }

        //TODO::
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

    public void setData(MainApp mainApp, User user, Future<List<Appointment>> result){
        this.mainApp = mainApp;
        this.user = user;
        this.usernameLabel.setText(user.getUserName());
        customerTableView.setItems(mainApp.customerList);

        new Thread(()-> {
            try {
                mainApp.appointmentList.clear();
                mainApp.appointmentList.addAll(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
        appointmentTableView.setItems(mainApp.appointmentList);
    }
}
