package main.java.com.planner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.com.planner.DataService.AppointmentDataService;
import main.java.com.planner.DataService.AuthenticationDataService;
import main.java.com.planner.DataService.CustomerDataService;
import main.java.com.planner.DataService.DBConnection;
import main.java.com.planner.Exceptions.AppOverlapException;
import main.java.com.planner.controller.*;
import main.java.com.planner.controller.Customer.CustomerDetailController;
import main.java.com.planner.controller.Customer.CustomerPageController;
import main.java.com.planner.controller.appointment.AppDetailController;
import main.java.com.planner.controller.appointment.AppointmentPageController;
import main.java.com.planner.controller.calendar.CalendarPageController;
import main.java.com.planner.model.Appointment;
import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MainApp extends Application {

    private static Stage window;
    private final String FXML = "fxml/";
    private final String CSS_PATH = "resources/css/style.css";
    private final String CUSTOMER_PAGE = "CustomerPage.fxml";
    private final String LOGIN_PAGE = "LoginPage.fxml";
    private final String APPOINTMENT_PAGE = "AppointmentPage.fxml";
    private final String CALENDAR_PAGE = "CalendarPage.fxml";
    private final String REPORT_PAGE = "ReportPage.fxml";
    private final String ICON_PATH = "resources/favicon.jpg";
    private boolean isReminded = false;
    private Stage detailsWindow;
    private AppointmentPageController appController;
    private AuthenticationDataService authDS;
    private CustomerDataService customerDS;
    private AppointmentDataService appointmentDS;
    private static ExecutorService service;
    private Future<List<User>> authResult;
    private Future<List<Customer>> customerResult;
    private Future<List<Appointment>> appointmentResult;
    private List<User> userList;
    public ResourceBundle bundle;
    public ObservableList<Customer> customerList;
    public ObservableList<Appointment> appointmentList;

    public static User user = new User.UserBuilder(1).username("test").password("test").active(true)
            .createDate(ZonedDateTime.now(ZoneId.systemDefault())).createdBy("Alex")
            .lastUpdate(ZonedDateTime.now(ZoneId.systemDefault())).LastUpdateBy("Alex").build();


    public static void main(String[] args) {
        service = Executors.newSingleThreadExecutor();
        service.submit(DBConnection::makeConnection);
        launch(args);
        DBConnection.closeConnection();
        if (!service.isTerminated()) service.shutdown();
    }

    @Override
    public void start(Stage primaryStage) {
        bundle = ResourceBundle.getBundle("LoginBundle", Locale.getDefault());
        window = primaryStage;
        window.setTitle("Planner");
        window.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
        window.setResizable(false);
        initData();
        loginPageLoad();
        window.show();
    }

    //initializes the data
    private void initData() {
        authDS = new AuthenticationDataService();
        customerDS = new CustomerDataService();
        appointmentDS = new AppointmentDataService();
        userList = new ArrayList<>();
        customerList = FXCollections.observableArrayList();
        appointmentList = FXCollections.observableArrayList();
        authResult = service.submit(authDS::getAllUsers);
        customerResult = service.submit(customerDS::getAllCustomers);
        appointmentResult = service.submit(appointmentDS::getAllAppointments);
        service.shutdown();
        new Thread(() -> {
            boolean isDone = false;
            while (!isDone) {
                isDone = service.isTerminated();
            }
            try {
                userList.clear();
                customerList.clear();
                appointmentList.clear();
                userList.addAll(authResult.get());
                customerList.addAll(customerResult.get());
                appointmentList.addAll(appointmentResult.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loginPageLoad() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML + LOGIN_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            LoginPageController loginController = loader.getController();
            loginController.setData(this, userList);
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void customersPageLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + CUSTOMER_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            CustomerPageController customerController = loader.getController();
            customerController.setData(this, user, customerDS);
            window.setScene(scene);
            if (!isReminded) {
                checkReminder();
                isReminded = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appointmentPageLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + APPOINTMENT_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            AppointmentPageController appointmentController = loader.getController();
            appointmentController.setData(this, user, appointmentDS, appointmentResult);
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calendarPageLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + CALENDAR_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            CalendarPageController appointmentController = loader.getController();
            appointmentController.setData(this, user, appointmentResult);
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportPageLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + REPORT_PAGE));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            ReportPageController reportController = loader.getController();
            reportController.setData(this, user, userList);
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void customerDetailPageLoad(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/CustomerDetailPage.fxml"));

            detailsWindow = new Stage();
            detailsWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            detailsWindow.setScene(scene);

            CustomerDetailController customerDetailController = loader.getController();
            customerDetailController.setData(this, customer, user.getUserName());

            Parent root = loader.getRoot();
            root.requestFocus();
            detailsWindow.showAndWait();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void appDetailPageLoad(AppointmentPageController appController, Appointment appointment, Customer selectedCustomer, User user) {
        this.appController = appController;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/AppDetailPage.fxml"));

            detailsWindow = new Stage();
            detailsWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            detailsWindow.setScene(scene);

            AppDetailController appDetailController = loader.getController();
            appDetailController.setData(this, appointment, selectedCustomer, user);

            Parent root = loader.getRoot();
            root.requestFocus();
            detailsWindow.showAndWait();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomer(Customer customer, boolean isExisting) {
        if (detailsWindow != null)
            detailsWindow.close();
        if (customer != null) {
            if (isExisting) {
                customerDS.updateCustomer(customer);
            } else {
                customerDS.addCustomer(customer);
            }
            customerList.clear();
            customerList.addAll(customerDS.getAllCustomers());
        }
    }

    public void deleteCustomer(Customer selectedCustomer) {
        if (selectedCustomer == null)
            return;
        List<Appointment> foundApp = appointmentList.stream().filter(app -> app.getCustomerId() == selectedCustomer.getCustomerId()).collect(Collectors.toList());
        if (!foundApp.isEmpty()) {
            foundApp.forEach(app -> appointmentDS.deleteAppointment(app));
            appointmentList.removeAll(foundApp);
        }
        customerDS.deleteCustomer(selectedCustomer);
        customerList.remove(selectedCustomer);
    }

    //throws exception if new appointment overlaps with existing one
    public void saveAppointment(Appointment appointment, boolean isExisting) throws AppOverlapException {
        if(appointment == null)
            return;
        List<Appointment> foundApp = appointmentList.stream()
                .filter(app -> app.getCustomerId() == appointment.getCustomerId()
                        && app.getStart().toLocalDate().equals(appointment.getStart().toLocalDate())).collect(Collectors.toList());
        if (!foundApp.isEmpty()) {
            for (Appointment app : foundApp) {
                LocalTime startTime = app.getStart().toLocalTime();
                LocalTime endTime = app.getEnd().toLocalTime();
                LocalTime newAppStartTime = appointment.getStart().toLocalTime();
                LocalTime newAppEndTime = appointment.getEnd().toLocalTime();
                if (newAppStartTime.equals(startTime) || newAppStartTime.isAfter(startTime) && newAppStartTime.isBefore(endTime)
                        ||newAppEndTime.isAfter(startTime) && newAppEndTime.isBefore(endTime))
                    throw new AppOverlapException("Overlapping with another appointment time");
            }
        }
        if (detailsWindow != null)
            detailsWindow.close();

        if (isExisting) {
            appointmentDS.updateAppointment(appointment);
        } else {
            appointmentDS.addAppointment(appointment);
        }
        appointmentList.clear();
        appointmentList.addAll(appointmentDS.getAllAppointments());
        if (this.appController != null)
            this.appController.updateAppList();
    }

    public void showAlertMessage(final String header, final String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selection Warning");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    //Checks appointments start time in next 15 mins
    private void checkReminder() {
        if (appointmentList.size() == 0)
            return;
        LocalDateTime currentUTCTime = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        appointmentList.forEach(app -> {   // for each appointment it checks of the start time in less then 15 minutes
            LocalDate appLocalDate = app.getStart().toLocalDate();
            if (currentUTCTime.toLocalDate().equals(appLocalDate)) {
                Duration timeUntilApp = Duration.between(currentUTCTime.toLocalTime(), app.getStart().toLocalTime());
                if (timeUntilApp.getSeconds() >= 0 && timeUntilApp.getSeconds() < 900) {
                    sb.append(String.format("You have a meeting with %s at %s\n", app.customerNameProperty().get(),
                            app.getStart().toLocalTime()));
                }
            }
        });
        if (sb.length() > 0)
            showAlertMessage("Reminder", sb.toString());
    }
}
