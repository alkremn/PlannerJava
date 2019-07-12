package main.java.com.planner.DataService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DBNAME = "U04D7a";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + DBNAME;
    private static final String USER = "U04D7a";
    private static final String PASSWORD = "53688205771";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static Connection connection;

    public static Connection getConnection(){return connection;}

    //opens new connection to the database
    public static void makeConnection(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection Successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //closes the connection to the database
    public static void closeConnection() {
        try{
            connection.close();
            System.out.println("Connection Closed");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
