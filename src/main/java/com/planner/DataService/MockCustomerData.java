package main.java.com.planner.DataService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.planner.model.Address;
import main.java.com.planner.model.City;
import main.java.com.planner.model.Country;
import main.java.com.planner.model.Customer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

//public class MockCustomerData {
//    public static ObservableList<Customer> getCustomerList(){
//
//        Country country = new Country(1, "USA", ZonedDateTime.now(ZoneId.of("UTC")),
//                "Alex", ZonedDateTime.now(ZoneId.of("UTC")), "Alex");
//
//        City city = new City(1, "Orem", country, ZonedDateTime.now(ZoneId.of("UTC")),
//                "Alex", ZonedDateTime.now(ZoneId.of("UTC")), "Alex");
//
//        Address address = new Address(1, "1028 W 1375 N", "", city, "84057", "3852218919",
//                ZonedDateTime.now(ZoneId.of("UTC")), "Alex", ZonedDateTime.now(ZoneId.of("UTC")),
//                "Alex");
//
//
//
//        return FXCollections.observableArrayList(
//                new Customer(1,"Alexey Kremnev", address, true,
//                        ZonedDateTime.now(ZoneId.of("UTC")),
//                        "Alex", ZonedDateTime.now(ZoneId.of("UTC")),"Alex"),
//
//                new Customer(1,"Ben Someone", address, false,
//                        ZonedDateTime.now(ZoneId.of("UTC")),
//                        "Alex",ZonedDateTime.now(ZoneId.of("UTC")),"Alex"));
//    }
//}
