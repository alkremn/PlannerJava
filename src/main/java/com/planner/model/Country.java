package main.java.com.planner.model;
import java.time.ZonedDateTime;
import java.util.Date;

public class Country {
    private int countryId;
    private String name;
    private ZonedDateTime createDate;
    private String CreatedBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdateBy;

    public Country(int countryId, String name, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.name = name;
        this.createDate = createDate;
        CreatedBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
