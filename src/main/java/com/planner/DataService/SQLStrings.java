package main.java.com.planner.DataService;

public class SQLStrings {
    public static final String countryTableValues = "country, createDate, createdBy, lastUpdate, lastUpdateBy";
    public static final String cityTableValues = "city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy";
    public static final String addressTableValues = "address, address2, cityId, postalCode, phone, createDate,createdBy, lastUpdate, lastUpdateBy";
    public static final String customerTableValues = "customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy";
    public static final String appointmentTableValues = "customerId, userId, title, description, location, contact, type, url, start, end, createDate, " +
            "createdBy, lastUpdate, lastUpdateBy";

    public static final String allCustomersQuery = "SELECT customer.customerid, customer.customername, customer.active, address.addressid, \n" +
            "\taddress.address, address.address2, address.postalcode, address.phone, city.cityid, \n" +
            "    city.city, country.countryid, country.country, customer.createdate, customer.createdBy, \n" +
            "    customer.lastUpdate, customer.lastUpdateBy FROM customer \n" +
            "    INNER JOIN address ON customer.addressId = address.addressId \n" +
            "    INNER JOIN city ON address.cityid = city.cityid \n" +
            "    INNER JOIN country ON city.countryid = country.countryid;";


    public static final String allAppointmentsByCustomerId = "";




    public static final String asdf = "select max(customerid) from customer where customerName like 'Al%';";
}
