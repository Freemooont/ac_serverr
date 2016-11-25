package com.example.dto;

public class Location {


    private double latitude;

    private double longitude;

    private String address;

    public Location() {
    }

    public Location(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }


    public String getCoordinates() {
        return latitude + "," + longitude;
    }




}