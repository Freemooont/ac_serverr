package com.example.controllers.placesActivity;

import com.example.entity.feeds.*;
import com.example.entity.palcesActivity.PlaceItem;
import com.example.entity.palcesActivity.PlaceItemCustom;
import com.example.entity.palcesActivity.PlaceItemPost;

/**
 * Created by mike on 11/15/16.
 */
public class PlaceItemValidator {

    static <T extends PlaceItemCustom> boolean validate(T placeItem) {

        if (placeItem instanceof PlaceItem) {
            return true;
        }else if(placeItem instanceof PlaceItemPost)
            return true;
        return true;
    }
}
