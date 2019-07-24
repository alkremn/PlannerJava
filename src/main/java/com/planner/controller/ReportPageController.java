package main.java.com.planner.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.NumAppTypesByMonth;
import main.java.com.planner.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportPageController {

    private MainApp mainApp;
    private ObservableList<NumAppTypesByMonth> firstReport = FXCollections.observableArrayList();
    private List<String> months = new ArrayList<>(Arrays.asList("January", "February", "March",
            "April", "May", "June", "July", "August", "September", "October", "November", "December"));

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<NumAppTypesByMonth> firstTableView;

    @FXML
    private TableColumn<NumAppTypesByMonth, String> monthColumn;

    @FXML
    private TableColumn<NumAppTypesByMonth, String> typeColumn;

    @FXML
    private TableView<?> secondTableView;

    @FXML
    private TableColumn<?, String> consultantColumn;

    @FXML
    private TableColumn<?, String> appTimeColumn;

    @FXML
    private TableView<?> thirdTableView;

    @FXML
    private TableColumn<?, String> customerNameColumn;

    @FXML
    private TableColumn<?, String> numAppColumn;

    @FXML
    private void initialize(){
        monthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());


        monthColumn.setStyle( "-fx-alignment: CENTER;");
        typeColumn.setStyle( "-fx-alignment: CENTER;");

        firstTableView.setItems(firstReport);
    }

    @FXML
    private void customerButtonHandler(ActionEvent event){ mainApp.customersPageLoad();}

    @FXML
    private void appointmentButtonHandler(ActionEvent event) {
        mainApp.appointmentPageLoad();
    }

    @FXML
    private void calendarButtonHandler(ActionEvent event) {
        mainApp.calendarPageLoad();
    }

    public void setData(MainApp mainApp, User user){
        this.mainApp = mainApp;
        this.usernameLabel.setText(user.getUserName());
        createFirstReport();
    }

    private void createFirstReport(){
        mainApp.appointmentList.forEach(a -> {
            String month = months.get(a.getStart().getMonth().getValue() - 1);
            System.out.println(month);
        });
    }

}
