package com.example.dto;

import javax.persistence.Column;

/**
 * Created by mike on 11/24/16.
 */
public class PlaceLocation {

    String area_id;

    String locality_id;

    public PlaceLocation() {
    }

    public PlaceLocation(String area_id, String locality_id) {
        this.area_id = area_id;
        this.locality_id = locality_id;
    }

    public void setLocality_id(String locality_id) {
        this.locality_id = locality_id;
    }

    public void setArea_id(String area_id) {

        this.area_id = area_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public String getLocality_id() {
        return locality_id;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlaceLocation) {
            PlaceLocation location = (PlaceLocation) obj;
            if (location.area_id == area_id && location.locality_id == locality_id)
                return true;
            else return false;
        }
        return false;
    }
}
