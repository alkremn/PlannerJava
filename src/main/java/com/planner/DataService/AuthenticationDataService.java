package main.java.com.planner.DataService;

import main.java.com.planner.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AuthenticationDataService {

    public static List<User> getAllUsers(){
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try{
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                users.add(createUser(result));
            }
        } catch (SQLException | ParseException e){
            e.printStackTrace();
        }
        return users;
    }

    public static User findUser(String user, String pass){
        String sql = "SELECT * FROM user WHERE userName = '" + user + "' AND password = '" + pass + "';";
        User foundUser = null;
        try{
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            if(result.first()){
                foundUser = createUser(result);
            }
        } catch (SQLException | ParseException e){
            e.printStackTrace();
        }
        return foundUser;
    }


    private static User createUser(ResultSet result) throws SQLException, ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        User user = null;

        int id = result.getInt("userid");
        String username = result.getString("username");
        String password = result.getString("password");
        boolean active = result.getBoolean("active");
        Date createDate = dateFormat.parse(result.getString("createdate"));
        String createdBy = result.getString("createdby");
        Date lastUpdateDate = dateFormat.parse(result.getString("lastupdate"));
        String lastUpdateBy = result.getString("lastupdateby");

        String createDateString = result.getString("createDate");
        String updateDateString = result.getString("lastUpdate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime utcCreateDate = LocalDateTime.parse(createDateString, formatter);
        ZonedDateTime zonedUTCCreateDateTime = ZonedDateTime.of(utcCreateDate, ZoneId.of("UTC"));

        LocalDateTime utcUpdateDate = LocalDateTime.parse(updateDateString, formatter);
        ZonedDateTime zonedUTCUpdateDateTime = ZonedDateTime.of(utcUpdateDate, ZoneId.of("UTC"));

        ZonedDateTime localCreateDate = zonedUTCCreateDateTime.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localUpdateDate = zonedUTCUpdateDateTime.withZoneSameInstant(ZoneId.systemDefault());


        user = new User.UserBuilder(id)
                .username(username)
                .password(password)
                .active(active)
                .createDate(localCreateDate)
                .createdBy(createdBy)
                .lastUpdate(localUpdateDate)
                .LastUpdateBy(lastUpdateBy)
                .build();

        return user;
    }
}
