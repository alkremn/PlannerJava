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
        String allAppointmentsSQL = "SELECT appointment.appointmentId, appointment.customerId, appointment.userId, appointment.title, appointment.description, \n" +
                "                appointment.location, appointment.contact, appointment.type, appointment.url, appointment.start, appointment.end, \n" +
                "                appointment.createDate, appointment.createdBy, appointment.lastUpdate, appointment.lastUpdateBy, customer.customerName FROM appointment\n" +
                "                LEFT JOIN customer ON customer.customerid = appointment.customerid;";
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
            System.out.println(e.getMessage());
        }
        if(result > 0) System.out.println("Appointment Added!!!");
        return result > 0;
    }

    public boolean deleteAppointment(Appointment appointment){
        String appSQL = String.format("DELETE FROM appointment WHERE appointmentId = %d;",appointment.getId());
        int result = 0;

        try{
            Statement statement = DBConnection.getConnection().createStatement();
            result = statement.executeUpdate(appSQL);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return result > 0;
    }


    private Appointment createAppointment(final ResultSet entry){
        Appointment appointment = null;

        try{
            int customerId = entry.getInt("customerId");

            int id = entry.getInt("appointmentId");
            int userId = entry.getInt("userId");
            String title = entry.getString("title");
            String description = entry.getString("description");
            String location = entry.getString("location");
            String contact = entry.getString("contact");
            String type = entry.getString("type");
            String url = entry.getString("url");
            String customerName = entry.getString("customerName");

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
                    createdBy, localUpdateDate.toLocalDateTime(), lastUpdateBy, customerName);
        } catch(SQLException e){
           System.out.println(e.getMessage());
        }
        return appointment;
    }

    public boolean updateAppointment(Appointment appointment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String appUpdateValues = getAppUpdateValueString(appointment, formatter);
        String updateAppSQL = String.format("UPDATE appointment SET %s WHERE appointmentId = %s;",appUpdateValues, appointment.getId());
        int result = 0;
        try{
            Statement statement = DBConnection.getConnection().createStatement();
            result = statement.executeUpdate(updateAppSQL);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result > 0;
    }


    private String getAppValueString(Appointment appointment, DateTimeFormatter formatter){
        ZonedDateTime appCreateDate = appointment.getCreateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcCreateDate = appCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = utcCreateDate.toLocalDateTime();

        ZonedDateTime appUpdateDate = appointment.getLastUpdateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = appUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("%d, %d,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'",
                appointment.getCustomerId(),appointment.getUserId(), appointment.getTitle(), appointment.getDescription(),appointment.getLocation(),
                appointment.getContact(), appointment.getType(), appointment.getUrl(), appointment.getStart(), appointment.getEnd(),
                createDate.format(formatter),appointment.getCreateBy(),
                updateDate.format(formatter),appointment.getLastUpdateBy());
    }

    private String getAppUpdateValueString(Appointment appointment, DateTimeFormatter formatter) {
        ZonedDateTime appUpdateDate = appointment.getLastUpdateDate().atZone(ZoneId.systemDefault());
        ZonedDateTime utcUpdateDate = appUpdateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime updateDate = utcUpdateDate.toLocalDateTime();

        return String.format("title='%s',description='%s',location='%s',contact='%s',type='%s',url='%s',start='%s',end='%s',lastUpdate ='%s',lastUpdateBy ='%s'",
                appointment.getTitle(), appointment.getDescription(), appointment.getLocation(),
                appointment.getContact(), appointment.getType(), appointment.getUrl(), appointment.getStart().format(formatter),
                appointment.getEnd().format(formatter), updateDate.format(formatter), appointment.getLastUpdateBy());
    }
}
