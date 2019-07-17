package main.java.com.planner.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.com.planner.DataService.CustomerDataService;
import main.java.com.planner.DataService.MockCustomerData;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Address;
import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.util.Date;


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
    private TableColumn<Customer,String> customerNameColumn;

    @FXML
    private TableColumn<Customer, Address> addressColumn;

    @FXML
    private TableColumn<Customer, String> appointmentColumn;

    @FXML
    private TableColumn<Customer, Boolean> statusColumn;

    @FXML
    private TableColumn<Customer, Date> createDateColumn;

    @FXML
    private TableColumn<Customer, String> createByColumn;


    @FXML
    private ObservableList<Customer> customerList;


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
            customerDS.addCustomer(selectedCustomer);
        } else{
            mainApp.showAlertMessage("No customer selected", "please, select the customer in the table");
        }
    }

    @FXML
    private void appointmentButtonHandler(ActionEvent event) {
    }

    @FXML
    private void calendarButtonHandler(ActionEvent event) {
        mainApp.calendarPageLoad();
    }

    @FXML
    private void reportButtonHandler(ActionEvent actionEvent) {
        mainApp.reportPageLoad();
    }

    public void setData(MainApp mainApp, User user, CustomerDataService customerDS){
        this.mainApp = mainApp;
        this.user = user;
        this.customerDS = customerDS;
        customerTableView.setItems(MockCustomerData.getCustomerList());
        this.usernameLabel.setText(user.getUserName());
    }


}
