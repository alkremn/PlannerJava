package main.java.com.planner.DataService;

public class SQLStrings {
    public static final String countryTableValues = "country, createDate, createdBy, lastUpdate, lastUpdateBy";
    public static final String cityTableValues = "city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy";
    public static final String addressTableValues = "address, address2, cityId, postalCode, phone, createDate,createdBy, lastUpdate, lastUpdateBy";
    public static final String customerTableValues = "customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy";


    public static final String asdf = "select max(customerid) from customer where customerName like 'Al%';";
}
