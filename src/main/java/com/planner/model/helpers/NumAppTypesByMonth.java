package main.java.com.planner.model.helpers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NumAppTypesByMonth {
    private StringProperty month;
    public int scrum;
    public int presentation;

    public NumAppTypesByMonth(String month) {
        this.month = new SimpleStringProperty(month);
    }

    public StringProperty monthProperty() {
        return month;
    }

    public StringProperty dataProperty() {
        return new SimpleStringProperty(String.format("Scrum:            %d \nPresentation:  %d", scrum, presentation));
    }
}
