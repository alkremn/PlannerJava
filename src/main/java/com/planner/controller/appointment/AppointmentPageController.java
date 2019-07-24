package main.java.com.planner.controller.appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.com.planner.DataService.AppointmentDataService;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Appointment;
import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class AppointmentPageController {

    private MainApp mainApp;
    private User user;
    private AppointmentDataService appointmentDS;
    private Customer selectedCustomer;
    private ObservableList<Appointment> customerApps;

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
    private TableColumn<Appointment, String> appTypeColumn;

    @FXML
    private TableColumn<Appointment, String> appStartEndTimeColumn;

    @FXML
    private TableColumn<Appointment, String> appDateColumn;

    @FXML
    private TableColumn<Appointment, String> appCreateDateColumn;


    @FXML
    void initialize() {
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        customerNameColumn.setCellValueFactory((cellData -> cellData.getValue().nameProperty()));
        statusColumn.setCellValueFactory((cellData -> cellData.getValue().activeProperty()));
        createDateColumn.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());

        appTypeColumn.setCellValueFactory(cellDate -> cellDate.getValue().typeProperty());
        appStartEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startEndTimeProperty());
        appDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateStringProperty());
        appCreateDateColumn.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());

        customerIdColumn.setStyle("-fx-alignment: CENTER;");
        customerNameColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setStyle("-fx-alignment: CENTER;");
        createDateColumn.setStyle("-fx-alignment: CENTER;");

        appTypeColumn.setStyle("-fx-alignment: CENTER;");
        appStartEndTimeColumn.setStyle("-fx-alignment: CENTER;");
        appDateColumn.setStyle("-fx-alignment: CENTER;");
        appCreateDateColumn.setStyle("-fx-alignment: CENTER;");


        customerTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                selectedCustomer = newValue;
                int customerId = selectedCustomer.getCustomerId();
                List<Appointment> appointments = mainApp.appointmentList.stream().filter(app -> app.getCustomerId() == customerId).collect(Collectors.toList());
                customerApps.clear();
                customerApps.setAll(appointments);
            }
        });
    }
    @FXML
    private void addAppointmentHandler(ActionEvent event){
        Customer selectedCustomer= customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null)
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        else
            mainApp.appDetailPageLoad(this,null, selectedCustomer, user);
    }

    @FXML
    private void modifyAppointmentHandler(ActionEvent event){
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        Appointment selectedApp= appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        } else if (selectedApp == null) {
            mainApp.showAlertMessage("No appointment selected", "please, select the appointment in the table");
        } else{
            mainApp.appDetailPageLoad(this, selectedApp, selectedCustomer, user);
        }

    }

    @FXML
    private void deleteAppointmentHandler(ActionEvent event){
        Appointment selectedApp = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedApp == null){
            mainApp.showAlertMessage("No appointment selected", "please, select the appointment in the table");
        } else {
            if(appointmentDS.deleteAppointment(selectedApp)){
                customerApps.remove(selectedApp);
                mainApp.appointmentList.remove(selectedApp);
            }
        }
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

    public void setData(MainApp mainApp, User user, AppointmentDataService appointmentDS, Future<List<Appointment>> result){
        this.mainApp = mainApp;
        this.user = user;
        this.appointmentDS = appointmentDS;
        this.usernameLabel.setText(user.getUserName());
        customerTableView.setItems(mainApp.customerList);
        customerApps = FXCollections.observableArrayList();
        appointmentTableView.setItems(customerApps);
        new Thread(()-> {
            try {
                mainApp.appointmentList.clear();
                mainApp.appointmentList.addAll(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateAppList(){
        if(selectedCustomer != null) {
            int customerId = selectedCustomer.getCustomerId();
            List<Appointment> appointments = mainApp.appointmentList.stream().filter(app -> app.getCustomerId() == customerId).collect(Collectors.toList());
            customerApps.clear();
            customerApps.setAll(appointments);
        }
    }
}
