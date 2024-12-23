package com.ambulance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String url = "jdbc:postgresql://localhost:5432/ambulances_db"; // Update with your database name
    private final String user = "postgres"; // Update with your username
    private final String password = "Gr3n@de"; // Update with your password

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}



/*CREATE TABLE ambulances (
       id SERIAL PRIMARY KEY,
       license_plate VARCHAR(50) NOT NULL,
       status VARCHAR(20) NOT NULL
   );*/