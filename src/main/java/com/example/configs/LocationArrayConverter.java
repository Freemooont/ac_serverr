package com.example.configs;

import com.example.dto.Location;
import com.example.entity.upload.FeedMedia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 11/24/16.
 */
public class LocationArrayConverter implements AttributeConverter<List<Location>, String> {


    @Override
    public String convertToDatabaseColumn(List<Location> attribute) {

        Gson gson = new GsonBuilder().create();
        String dd = gson.toJson(attribute);
        System.out.println(dd + "------------------");
        return dd;

    }

    @Override
    public List<Location> convertToEntityAttribute(String dbData) {
        Gson gson = new GsonBuilder().create();
        return (ArrayList<Location>) gson.fromJson(dbData, new ListType<ArrayList<Location>>().getType());
    }

}
