package main.java.com.planner.Exceptions;

public class AppOverlapException  extends  Exception{
    private String message;

    public AppOverlapException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
