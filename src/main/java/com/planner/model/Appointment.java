package main.java.com.planner.model;

import java.time.ZonedDateTime;

public class Appointment {
    private int id;
    private int customerId;
    private int userId;
    private String Title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime updateDate;
    private String lastUpdatedBy;
}
