package com.example.entity.user;

import com.example.dto.PlaceInfo;
import com.example.dto.Places;

import java.util.HashMap;
import java.util.Map;

public class UserSettings {

    Map<String, PlaceInfo> showing_cities = new HashMap<>();

    public UserSettings() {
    }

    public  Map<String, PlaceInfo> getShowing_cities() {
        return showing_cities;
    }

    public UserSettings setShowing_cities( Map<String, PlaceInfo> showing_cities) {
        this.showing_cities = showing_cities;
        return this;
    }

    public void addCity(String id, PlaceInfo city) {
        showing_cities.put(id, city);
    }



}
