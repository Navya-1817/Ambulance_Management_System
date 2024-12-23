package com.ambulance.model;

public class Ambulance {
    private int id;
    private String licensePlate;
    private String status;

    public Ambulance(int id, String licensePlate, String status) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}