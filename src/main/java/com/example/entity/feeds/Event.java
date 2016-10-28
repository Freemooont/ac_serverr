package com.example.entity.feeds;

import com.example.configs.JpaArrayConverter;
import com.example.configs.JpaConverterJson;
import com.example.entity.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_feed_events")
public class Event implements Feed{
    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "user_id",nullable =false, insertable = false, updatable = false)
    Long user_id;

    @Column(name = "title",nullable =false)
    String title;

    @Column(name = "content",nullable =false)
    String content;


    @Convert(converter = JpaArrayConverter.class)
    @Column(name = "feed_media",nullable =false)
    JSONArray feed_media;


    @Convert(converter = JpaConverterJson.class)
    @Column(name = "location",nullable =false)
    JSONObject location;

    @Column(name = "area_id",nullable =false)
    String area_id="123";

    @Column(name = "locality_id",nullable =false)
    String locality_id="123";

    @Column(name = "datetime_post", nullable = false)
    Timestamp datetime_post;

    @Column(name = "datetime_start", nullable = false)
    Timestamp datetime_start;

    @Column(name = "datetime_end", nullable = false)
    Timestamp datetime_end;

    @JoinColumn(name = "user_id")
    @ManyToOne
    Profile user;

    transient Integer users_interested;

    transient Integer users_joined;

    transient Integer vote_status;

    public Timestamp getDatetime_post() {
        return datetime_post;
    }

    public void setDatetime_post(Timestamp datetime_post) {
        this.datetime_post = datetime_post;
    }

    public Timestamp getDatetime_start() {
        return datetime_start;
    }

    public void setDatetime_start(Timestamp datetime_start) {
        this.datetime_start = datetime_start;
    }

    public Event(Long user_id, String title, String content, JSONArray feed_media, JSONObject location, String area_id, String locality_id, Timestamp datetime_post, Timestamp datetime_start, Timestamp datetime_end, Profile user, Integer users_interested, Integer users_joined, Integer vote_status) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.feed_media = feed_media;
        this.location = location;
        this.area_id = area_id;
        this.locality_id = locality_id;
        this.datetime_post = datetime_post;
        this.datetime_start = datetime_start;
        this.datetime_end = datetime_end;
        this.user = user;
        this.users_interested = users_interested;
        this.users_joined = users_joined;
        this.vote_status = vote_status;
    }

    public String getArea_id() {

        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getLocality_id() {
        return locality_id;
    }

    public void setLocality_id(String locality_id) {
        this.locality_id = locality_id;
    }

    public Timestamp getDatetime_end() {
        return datetime_end;

    }

    public void setDatetime_end(Timestamp datetime_end) {
        this.datetime_end = datetime_end;
    }

    public Integer getUsers_interested() {
        return users_interested;
    }

    public void setUsers_interested(Integer users_interested) {
        this.users_interested = users_interested;
    }

    public Integer getUsers_joined() {
        return users_joined;
    }

    public void setUsers_joined(Integer users_joined) {
        this.users_joined = users_joined;
    }

    public Integer getVote_status() {
        return vote_status;
    }

    public void setVote_status(Integer vote_staus) {
        this.vote_status = vote_staus;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public Profile getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public void setUser(Profile user) {
        this.user = user;
    }

    public Event() {
    }



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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONArray getFeed_media() {
        return feed_media;
    }

    public void setFeed_media(JSONArray feed_media) {
        this.feed_media = feed_media;
    }

    public JSONObject getLocation() {
        return location;
    }

    public void setLocation(JSONObject location) {
        this.location = location;
    }


    public Timestamp getDate_time_post() {
        return datetime_post;
    }

    public void setDate_time_post(Timestamp date_time_post) {
        this.datetime_post = date_time_post;
    }

    public Timestamp getDate_time_start() {
        return datetime_start;
    }

    public void setDate_time_start(Timestamp date_time_start) {
        this.datetime_start = date_time_start;
    }

    public Timestamp getDate_time_end() {
        return datetime_end;
    }

    public void setDate_time_end(Timestamp date_time_end) {
        this.datetime_end = date_time_end;
    }


}
