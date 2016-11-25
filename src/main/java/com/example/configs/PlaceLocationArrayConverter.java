package com.example.configs;

import com.example.dto.Location;
import com.example.dto.PlaceLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 11/24/16.
 */
public class PlaceLocationArrayConverter implements AttributeConverter<List<PlaceLocation>, String> {


    @Override
    public String convertToDatabaseColumn(List<PlaceLocation> attribute) {

        Gson gson = new GsonBuilder().create();
        return gson.toJson(attribute);

    }

    @Override
    public List<PlaceLocation> convertToEntityAttribute(String dbData) {
        Gson gson = new GsonBuilder().create();
        return (ArrayList<PlaceLocation>) gson.fromJson(dbData, new ListType<ArrayList<PlaceLocation>>().getType());
    }

}
