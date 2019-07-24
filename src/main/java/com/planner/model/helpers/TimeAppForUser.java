package main.java.com.planner.model.helpers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TimeAppForUser {
    private StringProperty user;
    private StringProperty timeString;

    public TimeAppForUser(String user, String timeString) {
        this.user = new SimpleStringProperty(user);
        this.timeString = new SimpleStringProperty(timeString);
    }

    public StringProperty userProperty() {
        return user;
    }

    public StringProperty timeStringProperty() {
        return timeString;
    }
}
