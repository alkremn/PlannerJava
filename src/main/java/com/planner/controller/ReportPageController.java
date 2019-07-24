package main.java.com.planner.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.Appointment;
import main.java.com.planner.model.helpers.CusNumOfApps;
import main.java.com.planner.model.helpers.NumAppTypesByMonth;
import main.java.com.planner.model.User;
import main.java.com.planner.model.helpers.TimeAppForUser;

import java.util.*;
import java.util.stream.Collectors;

public class ReportPageController {

    private MainApp mainApp;
    private ObservableList<NumAppTypesByMonth> firstReport = FXCollections.observableArrayList();
    private ObservableList<TimeAppForUser> secondReport = FXCollections.observableArrayList();
    private ObservableList<CusNumOfApps> thirdReport = FXCollections.observableArrayList();
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
    private TableView<TimeAppForUser> secondTableView;

    @FXML
    private TableColumn<TimeAppForUser, String> consultantColumn;

    @FXML
    private TableColumn<TimeAppForUser, String> appTimeColumn;

    @FXML
    private TableView<CusNumOfApps> thirdTableView;

    @FXML
    private TableColumn<CusNumOfApps, String> customerNameColumn;

    @FXML
    private TableColumn<CusNumOfApps, String> numAppColumn;

    @FXML
    private void initialize(){
        //first tableView
        monthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());

        //second tableView
        consultantColumn.setCellValueFactory(cellData -> cellData.getValue().userProperty());
        appTimeColumn.setCellValueFactory(cellData -> cellData.getValue().timeStringProperty());

        //third tableView
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().cusNameProperty());
        numAppColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfAppProperty());


        monthColumn.setStyle( "-fx-alignment: CENTER;");
        typeColumn.setStyle( "-fx-alignment: CENTER;");

        consultantColumn.setStyle( "-fx-alignment: CENTER;");

        customerNameColumn.setStyle( "-fx-alignment: CENTER;");
        numAppColumn.setStyle( "-fx-alignment: CENTER;");

        firstTableView.setItems(firstReport);
        secondTableView.setItems(secondReport);
        thirdTableView.setItems(thirdReport);
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

    public void setData(MainApp mainApp, User user, List<User> userList){
        this.mainApp = mainApp;

        this.usernameLabel.setText(user.getUserName());
        createFirstReport();
        createSecondReport(userList);
        createThirdReport();
    }

    private void createFirstReport(){

        for(String month : months){
            List<Appointment> appList = mainApp.appointmentList.stream()
                    .filter(a -> a.getStart().getMonth().name().toLowerCase().equals(month.toLowerCase())).collect(Collectors.toList());
            if(!appList.isEmpty()) calculateData(appList, month);
        }
    }
    private void calculateData(List<Appointment> monthList, String month){
        NumAppTypesByMonth report = new NumAppTypesByMonth(month);
        for(Appointment app : monthList){
            if(app.getType().toLowerCase().equals("scrum"))
                 report.scrum++;
            else
                report.presentation++;
        }
        firstReport.add(report);
    }

    private void createSecondReport(List<User> userList){
        for(User user : userList){
            List<Appointment> appList = mainApp.appointmentList.stream()
                    .filter(a -> a.getUserId() == user.getUserId()).collect(Collectors.toList());
            if(!appList.isEmpty()) createUserTimeData(appList, user.getUserName());
        }
    }

    private void createThirdReport() {
        Map<String, List<Appointment>> cusAppDic = new HashMap<>();
        mainApp.appointmentList.forEach(a -> {
            String customerName = a.customerNameProperty().get().toLowerCase();
            if(cusAppDic.containsKey(customerName))
                cusAppDic.get(customerName).add(a);
            else
                cusAppDic.put(customerName, new ArrayList<>(Collections.singletonList(a)));
        });
        cusAppDic.forEach((key, appList) -> thirdReport.add(new CusNumOfApps(key,appList.size())));
    }

    private void createUserTimeData(List<Appointment> appList, String user){
        StringBuilder timeString = new StringBuilder();
        for(int i = 0; i < appList.size(); i++){
            timeString.append(appList.get(i).startEndTimeProperty().get());
            timeString.append(i == appList.size() - 1 ? "." : ", ");
            if(i % 2 == 0) timeString.append("\n");

        }
        secondReport.add(new TimeAppForUser(user, timeString.toString()));
    }
}
