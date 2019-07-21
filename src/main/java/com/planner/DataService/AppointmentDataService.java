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

    public boolean addAppointment(Appointment appointment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String appointmentValues = getAppValueString(appointment, formatter);

        String appointmentSQL = String.format("INSERT INTO appointment(%s) VALUES(%s)",SQLStrings.appointmentTableValues,appointmentValues);

        int result = 0;
        try{
            Statement statement = DBConnection.getConnection().createStatement();
            result = statement.executeUpdate(appointmentSQL);
        }catch (SQLException e){
            e.getMessage();
        }
        if(result > 0) System.out.println("Appointment Added!!!");
        return result > 0;
    }



    private Appointment createAppointment(final ResultSet entry){
        Appointment appointment = null;
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

            String createdBy = entry.getString("createdBy");
            String lastUpdateBy = entry.getString("lastUpdateBy");

            String createDateString = entry.getString("createDate");
            String updateDateString = entry.getString("lastUpdate");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime utcCreateDate = LocalDateTime.parse(createDateString, formatter);
            ZonedDateTime zonedUTCCreateDateTime = ZonedDateTime.of(utcCreateDate, ZoneId.of("UTC"));

            LocalDateTime utcUpdateDate = LocalDateTime.parse(updateDateString, formatter);
            ZonedDateTime zonedUTCUpdateDateTime = ZonedDateTime.of(utcUpdateDate, ZoneId.of("UTC"));

            ZonedDateTime localCreateDate = zonedUTCCreateDateTime.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime localUpdateDate = zonedUTCUpdateDateTime.withZoneSameInstant(ZoneId.systemDefault());

            appointment = new Appointment(id, customerId, userId, title, description, location, contact, type,
            url, startTime, endTime, localCreateDate.toLocalDateTime(), createdBy, localUpdateDate.toLocalDateTime(), lastUpdateBy);
        } catch(SQLException e){
           System.out.println(e.getMessage());
        }
        return appointment;
    }

    private String getAppValueString(Appointment appointment, DateTimeFormatter formatter){
        ZonedDateTime appCreateDate = appointment.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = appCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime customerUpdateDate = appointment.getLastUpdateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = customerUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("%d, %d,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'",
                appointment.getCustomerId(),appointment.getUserId(), appointment.getTitle(), appointment.getDescription(),appointment.getLocation(),
                appointment.getContact(), appointment.getType(), appointment.getUrl(), appointment.getStart(), appointment.getEnd(),
                createDate.format(formatter),appointment.getCreateBy(),
                updateDate.format(formatter),appointment.getLastUpdateBy());
    }
}
