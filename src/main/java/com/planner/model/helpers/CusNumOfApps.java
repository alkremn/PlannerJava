package main.java.com.planner.model.helpers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CusNumOfApps {

    private StringProperty cusName;
    private StringProperty numberOfApp;

    public CusNumOfApps(String cusName, int numberOfApp) {
        this.cusName = new SimpleStringProperty(cusName);
        this.numberOfApp = new SimpleStringProperty(String.valueOf(numberOfApp));
    }

    public StringProperty cusNameProperty() {
        return cusName;
    }

    public StringProperty numberOfAppProperty() {
        return numberOfApp;
    }
}
