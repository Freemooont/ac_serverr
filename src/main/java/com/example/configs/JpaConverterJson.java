package com.example.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.AttributeConverter;

public class JpaConverterJson implements AttributeConverter<JSONObject, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();
  /*  @Override
    public String convertToDatabaseColumn(JSONObjectObject meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            return null;
            // or throw an error
        }
    }*/

    @Override
    public String convertToDatabaseColumn(JSONObject attribute) {

            return attribute.toString();
    }

    @Override
    public JSONObject convertToEntityAttribute(String dbData) {
        try {
            return (JSONObject) new JSONParser().parse(dbData);
        } catch (ParseException e) {
            e.printStackTrace();
            return new JSONObject();

        }
    }

}