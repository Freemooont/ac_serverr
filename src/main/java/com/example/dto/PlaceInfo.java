package com.example.dto;

import java.util.ArrayList;
import java.util.List;

public class PlaceInfo {

    public static final String TYPE_ADDRESS = "street_address";
    public static final String TYPE_LOCALITY = "locality";
    public static final String TYPE_ADMINISTRATIVE_LEVEL_1 = "administrative_area_level_1";
    public static final String TYPE_ADMINISTRATIVE_LEVEL_2 = "administrative_area_level_2";
    public static final String TYPE_ADMINISTRATIVE_BOTH = TYPE_ADMINISTRATIVE_LEVEL_1 + "|" + TYPE_ADMINISTRATIVE_LEVEL_2;
    public static final String TYPE_ALL = TYPE_LOCALITY + "|" + TYPE_ADMINISTRATIVE_BOTH;

    List<Result> results = new ArrayList<Result>();

    public List<Result> getResults() {
        return results;
    }

    public boolean hasAddress(){
        return results.size() > 0;
    }

    public class Result {


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
    }
}