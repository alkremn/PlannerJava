package main.java.com.planner.DataService;

import main.java.com.planner.model.Customer;
import main.java.com.planner.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Customer customer = null;

//        int id = result.getInt("customerid");
//        String customerName = result.getString("customerName");
//        String password = result.getString("password");
//        boolean active = result.getBoolean("active");
//        Date createDate = dateFormat.parse(result.getString("createdate"));
//        String createdBy = result.getString("createdby");
//        Date lastUpdateDate = dateFormat.parse(result.getString("lastupdate"));
//        String lastUpdateBy = result.getString("lastupdateby");
//
//        customer = new Customer(id, customerName, )

        return customer;
    }


}
