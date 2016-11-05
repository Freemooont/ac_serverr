package com.example.dto;

import java.util.ArrayList;
import java.util.List;

public class Places {

    public static final String TYPE_ADDRESS = "street_address";
    public static final String TYPE_LOCALITY = "locality";
    public static final String TYPE_ADMINISTRATIVE_LEVEL_1 = "administrative_area_level_1";
    public static final String TYPE_ADMINISTRATIVE_LEVEL_2 = "administrative_area_level_2";
    public static final String TYPE_ADMINISTRATIVE_BOTH = TYPE_ADMINISTRATIVE_LEVEL_1 + "|" + TYPE_ADMINISTRATIVE_LEVEL_2;
    public static final String TYPE_ALL = TYPE_LOCALITY + "|" + TYPE_ADMINISTRATIVE_BOTH;

    List<PlaceInfo> results = new ArrayList<PlaceInfo>();

    public List<PlaceInfo> getResults() {
        return results;
    }

    public boolean hasAddress(){
        return results.size() > 0;
    }


}