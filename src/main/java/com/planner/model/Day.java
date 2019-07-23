package main.java.com.planner.model;

import java.time.LocalDate;

public class Day {
    private LocalDate date;
    private boolean isTargetMonth;
    private boolean isToday;


    public Day(LocalDate date, boolean isTargetMonth, boolean isToday) {
        this.date = date;
        this.isTargetMonth = isTargetMonth;
        this.isToday = isToday;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isTargetMonth() {
        return isTargetMonth;
    }

    public boolean isToday() {
        return isToday;
    }
}
