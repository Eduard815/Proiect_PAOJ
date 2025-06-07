package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/banca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static Connection connection;

    private DatabaseConnection(){}

    public static Connection getConnection(){
        if (connection == null){
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection to the database succesful.");
            }
            catch (SQLException e){
                System.out.println("Failed to connect to the database.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
