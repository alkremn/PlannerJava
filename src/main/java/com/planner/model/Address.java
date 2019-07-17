package main.java.com.planner.model;

import java.util.Date;

public class Address {
    private int addressId;
    private String address;
    private String address2;
    private City city;
    private String postalCode;
    private String phone;
    private Date createDate;
    private String CreatedBy;
    private Date lastUpdate;
    private String lastUpdateBy;

    public Address(int addressId, String address, String address2, City city, String postalCode,
                   String phone, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.CreatedBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    @Override
    public String toString(){
        return address + address2;
    }

}
