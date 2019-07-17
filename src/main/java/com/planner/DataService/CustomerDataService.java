package main.java.com.planner.DataService;

import main.java.com.planner.model.Address;
import main.java.com.planner.model.City;
import main.java.com.planner.model.Country;
import main.java.com.planner.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class CustomerDataService {

    public List<Customer> getAllCustomers(){
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

    public boolean addCustomer(Customer customer){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


        String countryValues = getCountryString(customer.getAddress().getCity().getCountry(), dateFormat);
        String cityValues = getCityString(customer.getAddress().getCity(), dateFormat);
        String addressValues = getAddressString(customer.getAddress(), dateFormat);
        String customerValues = getCustomerString(customer, dateFormat);

        String sql = String.format("INSERT INTO country" + SQLStrings.countryTableValues +" VALUES(%s);",countryValues);

        int result = 0;
        try{
            Statement statement = DBConnection.getConnection().createStatement();
            result = statement.executeUpdate(sql);

        }catch (SQLException e){
            e.getMessage();
        }
        if(result > 0) System.out.println("Added!!!");
        return result > 0;
    }

    public boolean deleteCustomer(Customer customer){
        String sql = "";

        if(customer != null){

        }

        return true;
    }

    private Customer createCustomer(ResultSet result){
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

    private String getCustomerString(final Customer customer, final SimpleDateFormat dateFormat){
        return String.format("'%s', %s, %s, '%s', '%s', '%s', '%s'",
                customer.getName(), customer.getAddress(),customer.isActive(),
                dateFormat.format(customer.getCreateDate()),customer.getCreatedBy(),
                dateFormat.format(customer.getLastUpdate()),customer.getLastUpdateBy());
    }

    private String getAddressString(final Address address, final SimpleDateFormat dateFormat){
        return String.format("'%s', %s, %s, '%s', '%s', '%s', '%s'",
                address.getAddress(), address.getAddress2(),address.getCity().getCityId(), address.getPostalCode(),
                address.getPhone(), dateFormat.format(address.getCreateDate()),address.getCreatedBy(),
                dateFormat.format(address.getLastUpdate()),address.getLastUpdateBy());
    }

    private String getCityString(final City city, final SimpleDateFormat dateFormat){
        return String.format("%s,");
    }

    private String getCountryString(final Country country, final SimpleDateFormat dateFormat){
        return String.format("'%s', '%s', '%s', '%s', '%s'",
                    country.getName(), dateFormat.format(country.getCreateDate()),country.getCreatedBy(),
                dateFormat.format(country.getLastUpdate()),country.getLastUpdateBy());
    }
}
