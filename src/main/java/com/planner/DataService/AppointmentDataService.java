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

    public List<Appointment> getAllAppointments(){
        List<Appointment> appointments = new ArrayList<>();
        String allAppointmentsSQL = "SELECT * from appointment";

        try{
            Statement statement = DBConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(allAppointmentsSQL);
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

            String startTimeString = entry.getString("start");
            String endTimeString = entry.getString("end");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime utcStartDate = LocalDateTime.parse(startTimeString, formatter);
            ZonedDateTime zonedUTCStartDateTime = ZonedDateTime.of(utcStartDate, ZoneId.of("UTC"));

            LocalDateTime utcEndDate = LocalDateTime.parse(endTimeString, formatter);
            ZonedDateTime zonedUTCEndDateTime = ZonedDateTime.of(utcEndDate, ZoneId.of("UTC"));

            ZonedDateTime localStartDate = zonedUTCStartDateTime.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime localEndDate = zonedUTCEndDateTime.withZoneSameInstant(ZoneId.systemDefault());


            String createdBy = entry.getString("createdBy");
            String lastUpdateBy = entry.getString("lastUpdateBy");

            String createDateString = entry.getString("createDate");
            String updateDateString = entry.getString("lastUpdate");

            LocalDateTime utcCreateDate = LocalDateTime.parse(createDateString, formatter);
            ZonedDateTime zonedUTCCreateDateTime = ZonedDateTime.of(utcCreateDate, ZoneId.of("UTC"));

            LocalDateTime utcUpdateDate = LocalDateTime.parse(updateDateString, formatter);
            ZonedDateTime zonedUTCUpdateDateTime = ZonedDateTime.of(utcUpdateDate, ZoneId.of("UTC"));

            ZonedDateTime localCreateDate = zonedUTCCreateDateTime.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime localUpdateDate = zonedUTCUpdateDateTime.withZoneSameInstant(ZoneId.systemDefault());

            appointment = new Appointment(id, customerId, userId, title, description, location, contact, type,
            url, localStartDate.toLocalDateTime(), localEndDate.toLocalDateTime(), localCreateDate.toLocalDateTime(),
                    createdBy, localUpdateDate.toLocalDateTime(), lastUpdateBy);
        } catch(SQLException e){
           System.out.println(e.getMessage());
        }
        return appointment;
    }

    public boolean updateAppointment(Appointment appointment){
        return true;
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
