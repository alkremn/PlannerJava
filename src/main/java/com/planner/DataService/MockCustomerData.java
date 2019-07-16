package main.java.com.planner.DataService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.planner.model.Customer;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class MockCustomerData {
    public static ObservableList<Customer> getCustomerList(){

        return FXCollections.observableArrayList(
                new Customer(1,"Alexey Kremnev", 1, true,
                        Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)),
                        "Alex", Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)),"Alex"),
                new Customer(1,"Ben Someone", 2, false,
                        Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)),
                        "Alex", Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)),"Alex"));
    }
}
