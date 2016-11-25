package com.example.entity.feeds;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 11/9/16.
 */
public class FeedsCollection {

    List<Event> eventFeeds;

    List<Trouble> troubleFeeds;

    List<Suggestion> suggestionFeeds;

    List<Notify> notify_list;

    public FeedsCollection() {
        this.eventFeeds = new ArrayList<>();
        this.troubleFeeds = new ArrayList<>();
        this.suggestionFeeds = new ArrayList<>();
        this.notify_list = new ArrayList<>();
    }

    public List<Event> getEventFeeds() {
        return eventFeeds;
    }

    public void setEventFeeds(List<Event> eventFeeds) {
        this.eventFeeds = eventFeeds;
    }

    public List<Trouble> getTroubleFeeds() {
        return troubleFeeds;
    }

    public void setTroubleFeeds(List<Trouble> troubleFeeds) {
        this.troubleFeeds = troubleFeeds;
    }

    public List<Suggestion> getSuggestionFeeds() {
        return suggestionFeeds;
    }

    public void setSuggestionFeeds(List<Suggestion> suggestionFeeds) {
        this.suggestionFeeds = suggestionFeeds;
    }

    public List<Notify> getNotifies() {
        return notify_list;
    }

    public void setNotifies(List<Notify> notifies) {
        this.notify_list = notifies;
    }
}
