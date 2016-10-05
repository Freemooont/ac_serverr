package com.example.entity.votes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EventLikes implements Vote {
    @Id
    @GeneratedValue
    Long id;

    Long feedId;

    Integer likes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public EventLikes() {
    }

    public EventLikes(Long feedId, Integer likes) {
        this.feedId = feedId;
        this.likes = likes;
    }
}
