package main.java.com.planner.Exceptions;

public class AuthenticationException extends Exception {

    private String message;

    public AuthenticationException(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
