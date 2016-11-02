package com.example.entity.comments;

import com.example.entity.Profile;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TransferQueue;

/**
 * Created by mike on 11/2/16.
 */
public class FeedComment {

    private long id;

    private String body;

    private Timestamp datetime_post;

    private long feed_id;

    private int feed_type;

    private Profile user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(long feed_id) {
        this.feed_id = feed_id;
    }

    public int getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(int feed_type) {
        this.feed_type = feed_type;
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public <T extends Comments> T transform(){
        switch (feed_type){
            case 1:
                VoluntariesComments vol =
                        new VoluntariesComments(body, user, feed_id, datetime_post);
                return (T) vol;
            case 2:
                TroublesComments troublesComments =
                        new TroublesComments(body, user, feed_id, datetime_post);
                return (T) troublesComments;
            case 3:
                SuggestionComments suggestionComments =
                        new SuggestionComments(body, user, feed_id, datetime_post);
                return (T) suggestionComments;
            case 4:
                EventComments eventComments =
                        new EventComments(body, user, feed_id, datetime_post);
                return (T) eventComments;
            default: return null;
        }
    }

    public void setDatetime_post(Timestamp datetime_post) {
        this.datetime_post = datetime_post;
    }
}
