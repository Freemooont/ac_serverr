package com.example.configs;

import com.example.dto.PlaceInfo;
import com.example.dto.Places;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.AttributeConverter;

public class JpaConverterPlaceInfo implements AttributeConverter<PlaceInfo, String> {

    Gson gson;

    public JpaConverterPlaceInfo() {
        gson = new GsonBuilder().create();
    }

    @Override
    public String convertToDatabaseColumn(PlaceInfo attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public PlaceInfo convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, PlaceInfo.class);
    }
}