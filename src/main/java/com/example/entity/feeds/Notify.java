package com.example.entity.feeds;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_notify")
public class Notify {
    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    private Long id;

    @Column(name = "user_id",nullable =false)
    private Long user_id;

    @Column(name = "feed_id",nullable =false)
    private Long feed_id;

    @Column(name = "feed_type",nullable =false)
    private Integer feed_type;

    @Column(name = "datetime_post",nullable =false)
    private Timestamp datetime_post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(Long feed_id) {
        this.feed_id = feed_id;
    }

    public Integer getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(Integer feed_type) {
        this.feed_type = feed_type;
    }

    public Timestamp getDatetime_post() {
        return datetime_post;
    }

    public void setDatetime_post(Timestamp datetime_post) {
        this.datetime_post = datetime_post;
    }

    public Notify() {
    }

    public Notify(Long user_id, Long feed_id, Integer feed_type, Timestamp datetime_post) {
        this.user_id = user_id;
        this.feed_id = feed_id;
        this.feed_type = feed_type;
        this.datetime_post = datetime_post;
    }
}
