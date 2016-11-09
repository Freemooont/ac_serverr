package com.example.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import javax.persistence.AttributeConverter;


public class JpaArrayConverter  implements AttributeConverter<JSONArray, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JSONArray attribute) {

        return attribute.toString();
    }

    @Override
    public JSONArray convertToEntityAttribute(String dbData) {
        try {
            System.out.println(dbData + " ---------------");
            return (JSONArray)new JSONParser().parse(dbData);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();

        }
    }
}

