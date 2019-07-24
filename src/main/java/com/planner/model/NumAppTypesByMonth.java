package main.java.com.planner.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NumAppTypesByMonth {
    private StringProperty month;
    private StringProperty data;

    public NumAppTypesByMonth(String month, String data) {
        this.month = new SimpleStringProperty(month);
        this.data = new SimpleStringProperty(data);
    }

    public StringProperty monthProperty() {
        return month;
    }

    public StringProperty dataProperty() {
        return data;
    }
}
