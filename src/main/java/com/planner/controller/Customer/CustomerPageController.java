package main.java.com.planner.controller.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.com.planner.DataService.CustomerDataService;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Address;
import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CustomerPageController {

    private MainApp mainApp;
    private User user;
    private CustomerDataService customerDS;

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer,String> customerNameColumn, createByColumn, appointmentColumn;

    @FXML
    private TableColumn<Customer, Address> addressColumn;

    @FXML
    private TableColumn<Customer, Boolean> statusColumn;

    @FXML
    private TableColumn<Customer, String> createDateColumn;

    //Default constructor
    public CustomerPageController() {}

    @FXML
    void initialize(){
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        customerNameColumn.setCellValueFactory((cellData -> cellData.getValue().nameProperty()));
        addressColumn.setCellValueFactory((cellData -> cellData.getValue().addressProperty()));
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
        createDateColumn.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());
        createByColumn.setCellValueFactory(cellData -> cellData.getValue().createdByProperty());

        customerIdColumn.setStyle( "-fx-alignment: CENTER;");
        customerNameColumn.setStyle( "-fx-alignment: CENTER;");
        addressColumn.setStyle( "-fx-alignment: CENTER;");
        statusColumn.setStyle( "-fx-alignment: CENTER;");
        createDateColumn.setStyle( "-fx-alignment: CENTER;");
        createByColumn.setStyle( "-fx-alignment: CENTER;");

    }

    @FXML
    private void newCustomerHandler(ActionEvent event){
       mainApp.customerDetailPageLoad(null);
    }

    @FXML
    private void modifyCustomerHandler(ActionEvent event){
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null)
            mainApp.customerDetailPageLoad(selectedCustomer);
        else
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
    }

    @FXML
    private void deleteCustomerHandler(ActionEvent event){
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null) {

            if(customerDS.deleteCustomer(selectedCustomer))
                mainApp.customerList.remove(selectedCustomer);
        } else{
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        }
    }

    @FXML
    private void appointmentButtonHandler(ActionEvent event) {
        mainApp.appointmentPageLoad();
    }

    @FXML
    private void calendarButtonHandler(ActionEvent event) {
        mainApp.calendarPageLoad();
    }

    @FXML
    private void reportButtonHandler(ActionEvent actionEvent) {
        mainApp.reportPageLoad();
    }

    public void setData(MainApp mainApp, User user, CustomerDataService customerDS, Future<List<Customer>> result){
        this.mainApp = mainApp;
        this.user = user;
        this.customerDS = customerDS;
        new Thread(()-> {
            try {
                mainApp.customerList.clear();
                mainApp.customerList.addAll(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
        customerTableView.setItems(mainApp.customerList);
        this.usernameLabel.setText(user.getUserName());
    }




}
