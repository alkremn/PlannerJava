package main.java.com.planner.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Customer {
    private IntegerProperty customerId;
    private StringProperty name;
    private ObjectProperty<Address> address;
    private BooleanProperty active;
    private ObjectProperty<LocalDateTime> createDate;
    private StringProperty createdBy;
    private ObjectProperty<LocalDateTime> lastUpdate;
    private StringProperty lastUpdateBy;


    public Customer(int customerId, String name, Address address, boolean active,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,String lastUpdateBy) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.name = new SimpleStringProperty(name);
        this.address =  new SimpleObjectProperty<>(address);
        this.active = new SimpleBooleanProperty(active);
        this.createDate = new SimpleObjectProperty<>(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleObjectProperty<>(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Address getAddress() {
        return address.get();
    }

    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    public boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public LocalDateTime getCreateDate() {
        return createDate.get();
    }

    public SimpleStringProperty createDateProperty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return new SimpleStringProperty(createDate.getValue().format(formatter));
    }


    public void setCreateDate(LocalDateTime createDate) {
        this.createDate.set(createDate);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public StringProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate.get();
    }

    public ObjectProperty<LocalDateTime> lastUpdateProperty() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public StringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }
}
