package com.databaseproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * the single place where the program connects to the Access database
 * DatabaseConnection
 */
public class DatabaseConnection {
    private String url = "jdbc:ucanaccess://database_project_maven_folder/Hotel_Management_DB.accdb"; 
    
    /**
     * method that creates a connection to the MS Access Database
     * @return Connection object used for querying data
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.getLogger(DatabaseConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return conn;
    }
}