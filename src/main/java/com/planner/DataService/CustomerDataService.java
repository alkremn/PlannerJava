package main.java.com.planner.DataService;

import main.java.com.planner.model.Address;
import main.java.com.planner.model.City;
import main.java.com.planner.model.Country;
import main.java.com.planner.model.Customer;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;


public class CustomerDataService {

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();

        try{
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQLStrings.allCustomersQuery);
            while(result.next()){
                customers.add(createCustomer(result));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    public boolean addCustomer(Customer customer){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String countryValues = getCountryValueString(customer.getAddress().getCity().getCountry(), formatter);
        String cityValues = getCityValueString(customer.getAddress().getCity(), formatter);
        String addressValues = getAddressValueString(customer.getAddress(), formatter);
        String customerValues = getCustomerValueString(customer, formatter);

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

    public boolean updateCustomer(Customer customer){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int countryId = customer.getAddress().getCity().getCountry().getCountryId();
        int cityId = customer.getAddress().getCity().getCityId();
        int addressId = customer.getAddress().getAddressId();
        String countryUpdateValues = getCountryUpdateValueString(customer.getAddress().getCity().getCountry(), formatter);
        String cityValues = getCityUpdateValueString(customer.getAddress().getCity(), formatter);
        String addressValues = getAddressUpdateValueString(customer.getAddress(), formatter);
        String customerValues = getCustomerUpdateValueString(customer, formatter);

        String countrySQL = String.format("UPDATE country SET %s WHERE countryId = %s;", countryUpdateValues, countryId);
        String citySQL = String.format("UPDATE city SET %s WHERE cityId = %s;",cityValues, cityId);
        String addressSQL = String.format("UPDATE address SET %s WHERE addressId = %s;", addressValues, addressId);
        String customerSQL = String.format("UPDATE customer SET %s WHERE customerId = %s;",customerValues,customer.getCustomerId());

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
        if(result > 0) System.out.println("Updated!!!");
        return result > 0;
    }



    private Customer createCustomer(ResultSet entry){
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
            String createBy = entry.getString("createdBy");
            String updateBy = entry.getString("lastUpdateBy");
            String createDateString = entry.getString("createDate");
            String updateDateString = entry.getString("lastUpdate");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime utcCreateDate = LocalDateTime.parse(createDateString, formatter);
            ZonedDateTime zonedUTCCreateDateTime = ZonedDateTime.of(utcCreateDate, ZoneId.of("UTC"));

            LocalDateTime utcUpdateDate = LocalDateTime.parse(updateDateString, formatter);
            ZonedDateTime zonedUTCUpdateDateTime = ZonedDateTime.of(utcUpdateDate, ZoneId.of("UTC"));

            ZonedDateTime localCreateDate = zonedUTCCreateDateTime.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime localUpdateDate = zonedUTCUpdateDateTime.withZoneSameInstant(ZoneId.systemDefault());

            customer = new Customer(id,cusName,
                    new Address(addressId,address,address2,
                            new City(cityId, city,
                                    new Country(countryId, country, localCreateDate.toLocalDateTime(), createBy, localUpdateDate.toLocalDateTime(), updateBy),
                                    localCreateDate.toLocalDateTime(), createBy,localUpdateDate.toLocalDateTime(),updateBy), postalCode, phone,
                            localCreateDate.toLocalDateTime(), createBy, localCreateDate.toLocalDateTime(), createBy),
                                    active, localCreateDate.toLocalDateTime(), createBy, localUpdateDate.toLocalDateTime(), updateBy);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public boolean deleteCustomer(Customer customer){
        if(customer != null){
            Address address = customer.getAddress();
            City city = address.getCity();
            Country country = city.getCountry();
            String countrySQL = String.format("DELETE FROM country WHERE countryId = %s; ",country.getCountryId());
            String citySQL = String.format("DELETE FROM city WHERE cityId = %s; ",city.getCityId());
            String addressSQL = String.format("DELETE FROM address WHERE addressId = %s; ",address.getAddressId());
            String customerSQL = String.format("DELETE FROM customer WHERE customerId = %s; ",customer.getCustomerId());
            int result = 0;
            try{
                Statement statement = DBConnection.getConnection().createStatement();
                //System.out.println(begin +customerSQL + countrySQL + citySQL + countrySQL + commit);
                result += statement.executeUpdate(customerSQL);
                result += statement.executeUpdate(addressSQL );
                result += statement.executeUpdate(citySQL);
                result += statement.executeUpdate(countrySQL);
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return result == 4;
        }

        return true;
    }

    private String getCountryUpdateValueString(final Country country, final DateTimeFormatter formatter){
        ZonedDateTime countryCreateDate = country.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = countryCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime countryUpdateDate = country.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = countryUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("country = '%s', createDate = '%s', createdBy = '%s', lastUpdate = '%s', lastUpdateBy = '%s'",
                country.getName(), createDate.format(formatter),country.getCreatedBy(),
                updateDate.format(formatter),country.getLastUpdateBy());
    }

    private String getCityUpdateValueString(final City city, final DateTimeFormatter formatter) {
        ZonedDateTime cityCreateDate = city.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = cityCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime cityUpdateDate = city.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = cityUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("city = '%s',createDate = '%s', createdBy = '%s', lastUpdate = '%s', lastUpdateBy = '%s'",
                city.getName(), createDate.format(formatter), city.getCreatedBy(),
                updateDate.format(formatter), city.getLastUpdateBy());
    }

    private String getAddressUpdateValueString(final Address address, final DateTimeFormatter formatter) {
        ZonedDateTime addressCreateDate = address.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = addressCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime addressUpdateDate = address.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = addressUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("address='%s', address2='%s',postalCode='%s',phone='%s',createDate = '%s', createdBy = '%s', lastUpdate = '%s', lastUpdateBy = '%s'",
                address.getAddress(), address.getAddress2(), address.getPostalCode(),
                address.getPhone(), createDate.format(formatter), address.getCreatedBy(),
                updateDate.format(formatter), address.getLastUpdateBy());

    }

    private String getCustomerUpdateValueString(final Customer customer, final DateTimeFormatter formatter) {
        ZonedDateTime customerCreateDate = customer.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = customerCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime customerUpdateDate = customer.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = customerUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("customerName='%s', active=%d, createDate = '%s', createdBy = '%s', lastUpdate = '%s', lastUpdateBy = '%s'",
                customer.getName(), customer.isActive() ? 1 : 0,
                createDate.format(formatter), customer.getCreatedBy(),
                updateDate.format(formatter), customer.getLastUpdateBy());
    }

    private String getCustomerValueString(final Customer customer, final DateTimeFormatter formatter){
        ZonedDateTime customerCreateDate = customer.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = customerCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime customerUpdateDate = customer.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = customerUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("'%s', LAST_INSERT_ID(), %d, '%s', '%s', '%s', '%s'",
                customer.getName(),customer.isActive() ? 1 : 0,
                createDate.format(formatter),customer.getCreatedBy(),
                updateDate.format(formatter),customer.getLastUpdateBy());
    }

    private String getAddressValueString(final Address address, final DateTimeFormatter formatter){
        ZonedDateTime addressCreateDate = address.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = addressCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime addressUpdateDate = address.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = addressUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("'%s', '%s', LAST_INSERT_ID(), '%s', '%s', '%s', '%s','%s','%s'",
                address.getAddress(), address.getAddress2(), address.getPostalCode(),
                address.getPhone(), createDate.format(formatter),address.getCreatedBy(),
                updateDate.format(formatter),address.getLastUpdateBy());
    }

    private String getCityValueString(final City city, final DateTimeFormatter formatter){
        ZonedDateTime cityCreateDate = city.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = cityCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime cityUpdateDate = city.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = cityUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("'%s',LAST_INSERT_ID(),'%s','%s','%s','%s'",city.getName(),
                createDate.format(formatter),city.getCreatedBy(),
                updateDate.format(formatter), city.getLastUpdateBy());
    }

    private String getCountryValueString(final Country country, final DateTimeFormatter formatter){
        ZonedDateTime countryCreateDate = country.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = countryCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime countryUpdateDate = country.getLastUpdate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = countryUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("'%s', '%s', '%s', '%s', '%s'",
                    country.getName(), createDate.format(formatter),country.getCreatedBy(),
                    updateDate.format(formatter),country.getLastUpdateBy());
    }
}
