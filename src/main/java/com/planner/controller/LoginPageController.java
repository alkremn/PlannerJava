package main.java.com.planner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.planner.DataService.AuthenticationDataService;
import main.java.com.planner.Exceptions.AuthenticationException;
import main.java.com.planner.Exceptions.LoggerException;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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
            mainApp.customersPageLoad();
        }
    }


    private User CheckValidData(String user, String pass) throws AuthenticationException {
        user = user.trim().toLowerCase();
        pass = pass.trim();

        if (user.isEmpty() || pass.isEmpty())
            throw new AuthenticationException("Invalid credentials");

        User foundUser = AuthenticationDataService.findUser(user, pass);

        if(foundUser == null)
                throw new AuthenticationException("User not authenticated");

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

    public void setData(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
