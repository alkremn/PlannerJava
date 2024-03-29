package main.java.com.planner.model;

import java.time.ZonedDateTime;

public class User {
    private int userId;
    private String userName;
    private String password;
    private boolean active;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String LastUpdateBy;

    //private default constructor
    private User() {}

    public int getUserId(){ return this.userId; }
    public String getUserName() { return this.userName; }
    public String getPassword() { return  this.password; }

    public static class UserBuilder {
        private int userId;
        private String userName;
        private String password;
        private boolean active;
        private ZonedDateTime createDate;
        private String createdBy;
        private ZonedDateTime lastUpdate;
        private String LastUpdateBy;

        public UserBuilder(int userId){
            this.userId = userId;
        }
        public UserBuilder username(String username){
            this.userName = username;
            return this;
        }
        public UserBuilder password(String password){
            this.password = password;
            return this;
        }
        public UserBuilder active(boolean active){
            this.active = active;
            return this;
        }

        public UserBuilder createDate(ZonedDateTime createDate){
            this.createDate = createDate;
            return this;
        }
        public UserBuilder createdBy(String createdBy){
            this.createdBy = createdBy;
            return this;
        }

        public UserBuilder lastUpdate(ZonedDateTime lastUpdate){
            this.lastUpdate = lastUpdate;
            return this;
        }
        public UserBuilder LastUpdateBy(String LastUpdateBy){
            this.LastUpdateBy = LastUpdateBy;
            return this;
        }

        public User build(){
            User user = new User();
            user.userId = this.userId;
            user.userName = this.userName;
            user.password = this.password;
            user.active = this.active;
            user.createDate = this.createDate;
            user.createdBy = this.createdBy;
            user.lastUpdate = this.lastUpdate;
            user.LastUpdateBy = this.LastUpdateBy;
            return user;
        }
    }
}
