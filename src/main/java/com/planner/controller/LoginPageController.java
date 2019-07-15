package main.java.com.planner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.planner.DataService.DBConnection;
import main.java.com.planner.Exceptions.AuthenticationException;
import main.java.com.planner.Exceptions.LoggerException;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginPageController {


    private final String FILE_NAME = "log.txt";
    private final String CONNECTING_MESSAGE = "Connecting to server...Hold on!";
    private MainApp mainApp;

    static User user;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label messageLabel;

    @FXML
    private void loginButtonHandler(ActionEvent event) throws Exception {
        messageLabel.setText("");
        messageLabel.setVisible(false);
        try {
            user = CheckValidData(username.getText(), password.getText());

        } catch (AuthenticationException e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setVisible(true);
        }
        if (user != null) {
            MainApp.user = user;
            mainApp.CustomerPageLoad();
        }
    }


    private User CheckValidData(String user, String pass) throws AuthenticationException {
        user = user.trim().toLowerCase();
        pass = pass.trim();

        if (user.isEmpty() || pass.isEmpty())
            throw new AuthenticationException("Invalid credentials");

        String sql = "SELECT * FROM user WHERE userName = '" + user + "' AND password = '" + pass + "';";
        User foundUser = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.first()) {
                int id = result.getInt("userId");
                String username = result.getString("username");
                String password = result.getString("password");
                int active = result.getInt("active");
                Date createDate = dateFormat.parse(result.getString("createDate"));
                String createdBy = result.getString("createdBy");
                Date lastUpdateDate = dateFormat.parse(result.getString("lastUpdate"));
                String lastUpdateBy = result.getString("lastUpdateBy");

                foundUser = new User.UserBuilder(id)
                        .username(username)
                        .password(password)
                        .active(active)
                        .createDate(createDate)
                        .createdBy(createdBy)
                        .lastUpdate(lastUpdateDate)
                        .LastUpdateBy(lastUpdateBy)
                        .build();

            } else {
                throw new AuthenticationException("User not authenticated");
            }
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return foundUser;
    }

    private void LogUserIntoFile() throws LoggerException {
        Path path = Paths.get("").toAbsolutePath();
        String separator = File.separator;
        File logFilePath = new File(path + separator + FILE_NAME);
        String logString = String.format("%s, %s, %s\n", user.getUserId(), user.getUserName(), LocalDateTime.now());
        BufferedWriter out = null;
        try {
            if (!logFilePath.exists()) {
                logFilePath.createNewFile();
            }
            out = new BufferedWriter(new FileWriter(logFilePath, true));
            out.write(logString);
            out.close();
        } catch (IOException e) {
            throw new LoggerException("Unable to write to the file");
        }
    }

    public void setData(MainApp mainApp, Future<?> result) {
        this.mainApp = mainApp;

        try {
            result.get(5, TimeUnit.SECONDS);

        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            messageLabel.setText("Unable to connect to server");
        }

    }
}
