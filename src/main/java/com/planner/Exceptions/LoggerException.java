package main.java.com.planner.Exceptions;

public class LoggerException extends Exception {

    private String message;

    public LoggerException(final String message) {
        this.message = message;
    }

}
