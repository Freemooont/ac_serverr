package com.example.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 11/3/16.
 */
public class PlaceInfo {


    public String formatted_address;

    public String place_id;

    public List<String> types = new ArrayList<String>();

    public String getFormattedAddress() {
        return formatted_address;
    }

    public String getPlaceId() {
        return place_id;
    }

    public List<String> getTypes() {
        return types;
    }

    public PlaceInfo() {
    }


}
