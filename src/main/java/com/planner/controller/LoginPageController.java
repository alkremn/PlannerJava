package main.java.com.planner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.planner.Exceptions.AuthenticationException;
import main.java.com.planner.Exceptions.LoggerException;
import main.java.com.planner.MainApp;
import main.java.com.planner.model.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class LoginPageController {


    private final String FILE_NAME = "log.txt";
    private MainApp mainApp;
    private List<User> userList;
    private static User user;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label signInLabel, messageLabel;

    @FXML
    private Button loginButton;

    @FXML
    private void loginButtonHandler(ActionEvent event) {
        messageLabel.setText("");
        try {
            user = CheckValidData(username.getText(), password.getText());
        } catch (AuthenticationException | LoggerException e) {
            messageLabel.setText(e.getMessage());
        }
        if (user != null) {
            MainApp.user = user;
            mainApp.customersPageLoad();
        }
    }

    //validates input data
    private User CheckValidData(String usernameString, String passwordString) throws AuthenticationException, LoggerException {
        String username = usernameString.trim().toLowerCase();
        String password = passwordString.trim();

        if (username.isEmpty() || password.isEmpty())
            throw new AuthenticationException(mainApp.bundle.getString("invalidCred"));

        //searches user list for username and password and returns if found one
        Optional<User> foundUser = userList.stream().filter(u -> u.getUserName().equals(username) && u.getPassword().equals(password)).findAny();
        if (!foundUser.isPresent())
            throw new AuthenticationException(mainApp.bundle.getString("authUser"));
        user = foundUser.get();
        LogUserIntoFile();
        return user;

    }

    //log login user info into the file
    private void LogUserIntoFile() throws LoggerException {
        Path path = Paths.get("").toAbsolutePath();
        String separator = File.separator;
        File logFilePath = new File(path + separator + FILE_NAME);
        String logString = String.format("%s, %s, %s\n", user.getUserId(), user.getUserName(), ZonedDateTime.now());
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

    public void setData(MainApp mainApp, List<User> userList) {
        this.userList = userList;
        this.mainApp = mainApp;
        signInLabel.setText(mainApp.bundle.getString("signIn"));
        username.setPromptText(mainApp.bundle.getString("username"));
        password.setPromptText(mainApp.bundle.getString("password"));
        loginButton.setText(mainApp.bundle.getString("loginButton"));
    }
}
