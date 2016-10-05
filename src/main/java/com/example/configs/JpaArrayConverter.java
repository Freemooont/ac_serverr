package com.example.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.AttributeConverter;

/**
 * Created by freem on 9/27/16.
 */
public class JpaArrayConverter  implements AttributeConverter<JSONArray, String> {

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
    public String convertToDatabaseColumn(JSONArray attribute) {

        return attribute.toString();
    }

    @Override
    public JSONArray convertToEntityAttribute(String dbData) {
        try {
            return (JSONArray) new JSONParser().parse(dbData);
        } catch (ParseException e) {
            e.printStackTrace();
            return new JSONArray();

        }
    }
}
