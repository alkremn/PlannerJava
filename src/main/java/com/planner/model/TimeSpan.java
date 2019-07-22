package main.java.com.planner.model;

public class TimeSpan {
    public int hours;
    public int minutes;

    public TimeSpan(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return String.format("%d:%s", hours, minutes == 0 ? Integer.toString(minutes) + minutes : Integer.toString(minutes));
    }
}
