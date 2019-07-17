package main.java.com.planner.model;
import java.util.Date;

public class Country {
    private int countryId;
    private String name;
    private Date createDate;
    private String CreatedBy;
    private Date lastUpdate;
    private String lastUpdateBy;

    public Country(int countryId, String name, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.name = name;
        this.createDate = createDate;
        CreatedBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
}
