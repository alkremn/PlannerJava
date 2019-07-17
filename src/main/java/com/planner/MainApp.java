package main.java.com.planner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.com.planner.DataService.DBConnection;
import main.java.com.planner.controller.*;
import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainApp extends Application {

    private static Stage window;
    private final String FXML = "fxml/";
    private final String CSS_PATH = "resources/css/style.css";
    private final String CUSTOMER_PAGE= "CustomerPage.fxml";
    private final String LOGIN_PAGE= "LoginPage.fxml";
    private final String APPOINTMENT_PAGE = "AppointmentPage.fxml";
    private final String CALENDAR_PAGE = "CalendarPage.fxml";
    private final String REPORT_PAGE = "ReportPage.fxml";
    private final String ICON_PATH = "resources/favicon.jpg";
    public static ExecutorService service;
    private Stage detailsWindow;
    public static Future<?> result;
    public static User user = new User.UserBuilder(1).username("root").password("Root").active(true)
            .createDate(new Date()).createdBy("Alex").lastUpdate(new Date()).LastUpdateBy("Alex").build();


    public static void main(String[] args) {

        service = Executors.newSingleThreadExecutor();
        result =  service.submit(DBConnection::makeConnection);
        launch(args);
        DBConnection.closeConnection();
        if(!service.isTerminated()) service.shutdown();
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Planner");
        window.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));

        customerPageLoad();
        window.show();
    }

    private void loginPageLoad() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML + LOGIN_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            LoginPageController loginController = loader.getController();
            loginController.setData(this, result);
            window.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void customerPageLoad(){
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + CUSTOMER_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            CustomerPageController customerController = loader.getController();
            customerController.setData(this, user);
            window.setScene(scene);

       } catch (IOException e){
            e.printStackTrace();
       }
    }

    public void appointmentPageLoad(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + APPOINTMENT_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            AppointmentPageController appointmentController = loader.getController();
            appointmentController.setData(this, user);
            window.setScene(scene);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void calendarPageLoad(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + CALENDAR_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            CalendarPageController appointmentController = loader.getController();
            appointmentController.setData(this, user);
            window.setScene(scene);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void reportPageLoad(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + REPORT_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            ReportPageController appointmentController = loader.getController();
            appointmentController.setData(this, user);
            window.setScene(scene);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void customerPageLoad(Customer customer){
         try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/CustomerDetailPage.fxml"));

            detailsWindow = new Stage();
            detailsWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            detailsWindow.setScene(scene);

            CustomerDetailController customerDetailController = loader.getController();
            customerDetailController.setData(this, customer);

            Parent root = loader.getRoot();
            root.requestFocus();
            detailsWindow.showAndWait();

        } catch (
        IOException e){
            e.printStackTrace();
        }
    }
    public void saveNewCustomer(Customer customer){
        if(detailsWindow != null) {
            detailsWindow.close();
        }
        //TODO:: Save Customer if its not null
    }
}
