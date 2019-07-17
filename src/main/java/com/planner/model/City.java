package main.java.com.planner.model;

import java.util.Date;

public class City {
    private int cityId;
    private String name;
    private Country country;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdateBy;

    public City(int cityId, String name, Country country, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.cityId = cityId;
        this.name = name;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
}
