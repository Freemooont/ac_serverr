package com.example.controllers.feedControllers;

import com.example.entity.feeds.*;
import com.example.entity.palcesActivity.PlaceItem;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.web.bind.annotation.RestController;


public class FeedValidator {

    static <T extends Feed> boolean validate(T feed) {
        if (feed instanceof Event) {
            return true;

        } else if (feed instanceof Trouble) {
            return true;

        } else if (feed instanceof Voluntaries) {
            return true;

        } else if (feed instanceof Suggestion) {
            return true;

        } else if (feed instanceof PlaceItem) {
            return true;

        }
        return true;
    }
}
