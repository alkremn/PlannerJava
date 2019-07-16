package main.java.com.planner.DataService;

import main.java.com.planner.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDataService {

    public static List<Customer> getAllCustomers(){
        String sql = "SELECT * FROM customer";
        List<Customer> customers = new ArrayList<>();

        try{
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                customers.add(createCustomer(result));
            }
        } catch (SQLException  e){
            e.printStackTrace();
        }
        return customers;
    }

    private static Customer createCustomer(ResultSet result){
        return null;
    }


}
