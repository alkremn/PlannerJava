package main.java.com.planner.DataService;

import main.java.com.planner.model.Address;
import main.java.com.planner.model.City;
import main.java.com.planner.model.Country;
import main.java.com.planner.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class CustomerDataService {

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();

        try{
            Thread.sleep(3000);
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQLStrings.allCustomersQuery);
            while(result.next()){
                customers.add(createCustomer(result));
            }
        } catch (SQLException | InterruptedException e){
            e.printStackTrace();
        }
        return customers;
    }

    public boolean addCustomer(Customer customer){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


        String countryValues = getCountryValueString(customer.getAddress().getCity().getCountry(), dateFormat);
        String cityValues = getCityValueString(customer.getAddress().getCity(), dateFormat);
        String addressValues = getAddressValueString(customer.getAddress(), dateFormat);
        String customerValues = getCustomerValueString(customer, dateFormat);

        String countrySQL = String.format("INSERT INTO country(%s) VALUES(%s);", SQLStrings.countryTableValues, countryValues);
        String citySQL = String.format("INSERT INTO city(%s) VALUES(%s);",SQLStrings.cityTableValues, cityValues);
        String addressSQL = String.format("INSERT INTO address(%s) VALUES(%s);", SQLStrings.addressTableValues, addressValues);
        String customerSQL = String.format("INSERT INTO customer(%s) VALUES(%s)",SQLStrings.customerTableValues,customerValues);


        int result = 0;
        try{
            Statement statement = DBConnection.getConnection().createStatement();
            statement.executeUpdate(countrySQL);
            statement.executeUpdate(citySQL);
            statement.executeUpdate(addressSQL);
            result = statement.executeUpdate(customerSQL);

        }catch (SQLException e){
            e.getMessage();
        }
        if(result > 0) System.out.println("Added!!!");
        return result > 0;
    }

    private Customer createCustomer(ResultSet entry){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getDefault());

        Customer customer = null;
        try {
            int id = entry.getInt("customerId");
            String cusName = entry.getString("customerName");
            boolean active = entry.getBoolean("active");
            int addressId = entry.getInt("addressId");
            String address = entry.getString("address");
            String address2 = entry.getString("address2");
            String postalCode = entry.getString("postalCode");
            String phone = entry.getString("phone");
            int cityId = entry.getInt("cityId");
            String city = entry.getString("city");
            int countryId = entry.getInt("countryId");
            String country = entry.getString("country");
            Date createDate = dateFormat.parse(entry.getString("createDate"));
            Date updateDate = dateFormat.parse(entry.getString("lastUpdate"));
            String createBy = entry.getString("createdBy");
            String updateBy = entry.getString("lastUpdateBy");

            customer = new Customer(id,cusName,
                    new Address(addressId,address,address2,
                            new City(cityId, city,
                                    new Country(countryId, country, createDate, createBy, updateDate, updateBy),
                                    createDate, createBy,updateDate,updateBy), postalCode, phone,
                                    createDate, createBy, createDate, createBy),
                                    active, createDate, createBy, updateDate, updateBy);
        } catch(SQLException | ParseException e){
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public boolean deleteCustomer(Customer customer){
        String sql = "";

        if(customer != null){

        }

        return true;
    }




    private String getCustomerValueString(final Customer customer, final SimpleDateFormat dateFormat){
        return String.format("'%s', LAST_INSERT_ID(), %d, '%s', '%s', '%s', '%s'",
                customer.getName(),customer.isActive() ? 1 : 0,
                dateFormat.format(customer.getCreateDate()),customer.getCreatedBy(),
                dateFormat.format(customer.getLastUpdate()),customer.getLastUpdateBy());
    }

    private String getAddressValueString(final Address address, final SimpleDateFormat dateFormat){
        return String.format("'%s', '%s', LAST_INSERT_ID(), '%s', '%s', '%s', '%s','%s','%s'",
                address.getAddress(), address.getAddress2(), address.getPostalCode(),
                address.getPhone(), dateFormat.format(address.getCreateDate()),address.getCreatedBy(),
                dateFormat.format(address.getLastUpdate()),address.getLastUpdateBy());
    }

    private String getCityValueString(final City city, final SimpleDateFormat dateFormat){
        return String.format("'%s',LAST_INSERT_ID(),'%s','%s','%s','%s'",city.getName(),
                dateFormat.format(city.getCreateDate()),city.getCreatedBy(),
                dateFormat.format(city.getCreateDate()), city.getLastUpdateBy());
    }

    private String getCountryValueString(final Country country, final SimpleDateFormat dateFormat){
        return String.format("'%s', '%s', '%s', '%s', '%s'",
                    country.getName(), dateFormat.format(country.getCreateDate()),country.getCreatedBy(),
                dateFormat.format(country.getLastUpdate()),country.getLastUpdateBy());
    }
}
