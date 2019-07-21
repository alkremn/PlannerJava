package main.java.com.planner.DataService;

import main.java.com.planner.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDataService {

    public List<Appointment> getAllAppointmentsById(int customerId){
        List<Appointment> appointments = new ArrayList<>();

        try{
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQLStrings.allAppointmentsByCustomerId);
            while(result.next()){
                appointments.add(createAppointment(result));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    private Appointment createAppointment(final ResultSet entry){
        Customer customer = null;
        try {
            int id = entry.getInt("appointmentId");
            int customerId = entry.getInt("customerId");
            int userId = entry.getInt("userId");
            String title = entry.getString("title");
            String description = entry.getString("description");
            String location = entry.getString("location");
            String contact = entry.getString("contact");
            String type = entry.getString("type");
            String url = entry.getString("url");
            Time startTime = entry.getTime("start");
            Time endTime = entry.getTime("end");

            Date createBy = entry.getDate("createdBy");
            String updateBy = entry.getString("lastUpdateBy");

            String createDateString = entry.getString("createDate");
            String updateDateString = entry.getString("lastUpdate");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//            LocalDateTime utcCreateDate = LocalDateTime.parse(createDateString, formatter);
//            ZonedDateTime zonedUTCCreateDateTime = ZonedDateTime.of(utcCreateDate, ZoneId.of("UTC"));
//
//            LocalDateTime utcUpdateDate = LocalDateTime.parse(updateDateString, formatter);
//            ZonedDateTime zonedUTCUpdateDateTime = ZonedDateTime.of(utcUpdateDate, ZoneId.of("UTC"));
//
//            ZonedDateTime localCreateDate = zonedUTCCreateDateTime.withZoneSameInstant(ZoneId.systemDefault());
//            ZonedDateTime localUpdateDate = zonedUTCUpdateDateTime.withZoneSameInstant(ZoneId.systemDefault());
//
//            customer = new Customer(id,cusName,
//                    new Address(addressId,address,address2,
//                            new City(cityId, city,
//                                    new Country(countryId, country, localCreateDate, createBy, localUpdateDate, updateBy),
//                                    localCreateDate, createBy,localUpdateDate,updateBy), postalCode, phone,
//                            localCreateDate, createBy, localCreateDate, createBy),
//                    active, localCreateDate, createBy, localUpdateDate, updateBy);
        } catch(SQLException e){
           System.out.println(e.getMessage());
        }
        return null;
    }
}
