package main.java.com.planner.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

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
    private StringProperty lastUpdateBy;

    public Appointment(int id, int customerId, int userId, String title, String description, String location, String contact, String type, String url,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createBy, LocalDateTime lastUpdateDate, String lastUpdateBy) {
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
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
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

    public ObjectProperty<LocalDateTime> startEndTimeProperty() {
        return start;
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
    }

    public LocalDateTime getCreateDate() {
        return createDate.get();
    }

    public ObjectProperty<LocalDateTime> createDateProperty() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate.set(createDate);
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
}
