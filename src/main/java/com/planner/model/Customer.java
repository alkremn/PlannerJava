package main.java.com.planner.model;

import javafx.beans.property.*;

import java.util.Date;

public class Customer {
    private IntegerProperty customerId;
    private StringProperty name;
    private ObjectProperty<Address> address;
    private BooleanProperty active;
    private ObjectProperty<Date> createDate;
    private StringProperty createdBy;
    private ObjectProperty<Date> lastUpdate;
    private StringProperty lastUpdateBy;


    public Customer(int customerId, String name, Address address, boolean active,
                    Date createDate, String createdBy, Date lastUpdate,String lastUpdateBy) {
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

    public Date getCreateDate() {
        return createDate.get();
    }

    public ObjectProperty<Date> createDateProperty() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
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

    public Date getLastUpdate() {
        return lastUpdate.get();
    }

    public ObjectProperty<Date> lastUpdateProperty() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
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
