package com.example.configs;

import com.example.entity.upload.FeedMedia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.AttributeConverter;

public class FeedMediaConverter implements AttributeConverter<FeedMedia, String> {


    @Override
    public String convertToDatabaseColumn(FeedMedia attribute) {

        Gson gson = new GsonBuilder().create();
        return gson.toJson(attribute);

    }

    @Override
    public FeedMedia convertToEntityAttribute(String dbData) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(dbData, FeedMedia.class);
    }

}
