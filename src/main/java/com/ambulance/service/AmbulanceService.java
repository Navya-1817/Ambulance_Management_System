package com.ambulance.service;

import com.ambulance.exception.AmbulanceException;
import com.ambulance.model.Ambulance;
import com.ambulance.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AmbulanceService {
    private DatabaseConnector dbConnector;

    public AmbulanceService() {
        this.dbConnector = new DatabaseConnector();
    }

    public void addAmbulance(Ambulance ambulance) throws AmbulanceException {
        String query = "INSERT INTO ambulances (license_plate, status) VALUES (?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, ambulance.getLicensePlate());
            pstmt.setString(2, ambulance.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new AmbulanceException("Error adding ambulance: " + e.getMessage());
        }
    }

    public List<Ambulance> getAllAmbulances() throws AmbulanceException {
        List<Ambulance> ambulances = new ArrayList<>();
        String query = "SELECT * FROM ambulances";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String licensePlate = rs.getString("license_plate");
                String status = rs.getString("status");
                ambulances.add(new Ambulance(id, licensePlate, status));
            }
        } catch (SQLException e) {
            throw new AmbulanceException("Error retrieving ambulances: " + e.getMessage());
        }
        return ambulances;
    }

    public void updateAmbulanceStatus(int id, String status) throws AmbulanceException {
        String query = "UPDATE ambulances SET status = ? WHERE id = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new AmbulanceException("Error updating ambulance status: " + e.getMessage());
        }
    }

    public void deleteAmbulance(int id) throws AmbulanceException {
        String query = "DELETE FROM ambulances WHERE id = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new AmbulanceException("Error deleting ambulance: " + e.getMessage());
        }
    }
}
