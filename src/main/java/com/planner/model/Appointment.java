package main.java.com.planner.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Appointment {
    private IntegerProperty id;
    private IntegerProperty customerId;
    private IntegerProperty userId;
    private StringProperty Title;
    private StringProperty description;
    private StringProperty location;
    private StringProperty contact;
    private StringProperty type;
    private StringProperty url;
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private ObjectProperty<LocalDateTime> createDate;
    private StringProperty createBy;
    private ObjectProperty<LocalDateTime> lastUpdateDate;
    private StringProperty startEndTime;
    private StringProperty startDateString;
    private StringProperty lastUpdateBy;
    private StringProperty customerName;

    public Appointment(int id, int customerId, int userId, String title, String description, String location, String contact, String type, String url,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createBy, LocalDateTime lastUpdateDate, String lastUpdateBy, String customerName) {
        this.id = new SimpleIntegerProperty(id);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.userId = new SimpleIntegerProperty(userId);
        Title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.type = new SimpleStringProperty(type);
        this.url = new SimpleStringProperty(url);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
        this.createDate = new SimpleObjectProperty<>(createDate);
        this.createBy = new SimpleStringProperty(createBy);
        this.lastUpdateDate = new SimpleObjectProperty<>(lastUpdateDate);
        this.startEndTime = new SimpleStringProperty(createTimeString());
        this.startDateString = new SimpleStringProperty(createAppDateString());
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
        this.customerName = new SimpleStringProperty(customerName);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getTitle() {
        return Title.get();
    }

    public StringProperty titleProperty() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public LocalDateTime getStart() {
        return start.get();
    }

    public void setStart(LocalDateTime start) {
        this.start.set(start);
    }

    public LocalDateTime getEnd() {
        return end.get();
    }

    public ObjectProperty<LocalDateTime> endProperty() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end.set(end);
        this.startEndTime.set(createTimeString());
        this.startDateString.set(createAppDateString());
    }

    public LocalDateTime getCreateDate() {
        return createDate.get();
    }

    public StringProperty startEndTimeProperty(){
       return this.startEndTime;
    }

    public StringProperty startDateStringProperty(){
        return this.startDateString;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate.set(createDate);
    }
    public StringProperty createDateProperty(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        return new SimpleStringProperty(createDate.getValue().toLocalDate().format(formatter));
    }

    public String getCreateBy() {
        return createBy.get();
    }

    public StringProperty createByProperty() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy.set(createBy);
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate.get();
    }

    public ObjectProperty<LocalDateTime> lastUpdateDateProperty() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate.set(lastUpdateDate);
    }

    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public StringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdatedBy) {
        this.lastUpdateBy.set(lastUpdatedBy);
    }

    public StringProperty customerNameProperty(){ return customerName; }

    private String createTimeString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String startTime = this.start.getValue().format(formatter);
        String endTime = this.end.getValue().format(formatter);
        return startTime + "-" + endTime;
    }

    private String createAppDateString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        return start.getValue().format(formatter);
    }
    private String createDateString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        return createDate.getValue().format(formatter);
    }

}
