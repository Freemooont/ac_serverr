package com.example.dto;

public class Location {


    private double latitude;

    private double longitude;


    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getCoordinates() {
        return latitude + "," + longitude;
    }

}