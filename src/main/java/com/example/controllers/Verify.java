package com.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mike on 11/2/16.
 */
@RestController
public class Verify {

    @RequestMapping(path = "/verify")
    String returnq(){
        return "{\"showing_cities\":[{"+
                "         \"formatted_address\" : \"Chisinau, Moldova\",\n" +
                "         \"place_id\" : \"ChIJPSOipW58yUAR97pzmHD6zkk\",\n" +
                "         \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "      }]," +
                "\"type_subscribe\":{\n" +
                "\"1\":true,\n" +
                "\"2\":true\n" +
                "},\n" +
                "\"type_disable\":{\n" +
                "\"1\":true,\n" +
                "\"2\":true\n" +
                "}}";
    }
}
