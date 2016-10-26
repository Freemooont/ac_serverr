package com.example.entity.feeds;

import com.example.configs.JpaArrayConverter;
import com.example.configs.JpaConverterJson;
import com.example.entity.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_feed_suggestion")
public class Suggestion implements Feed{
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

    @Column(name = "place_id",nullable =false)
    String place_id = "123";

    @Column(name = "date_time_post", nullable = false)
    Timestamp datetime_post;

    @JoinColumn(name = "user_id")
    @ManyToOne
    Profile user;

    transient Integer users_supported;

    transient Integer users_rejected;

    transient Integer vote_status;

    public Integer getVote_status() {
        return vote_status;
    }

    public void setVote_status(Integer vote_status) {
        this.vote_status = vote_status;
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
    
    public Integer getUsers_supported() {
        return users_supported;
    }

    public void setUsers_supported(Integer users_supported) {
        this.users_supported = users_supported;
    }
    
    public Integer getUsers_rejected() {
        return users_rejected;
    }

    public void setUsers_rejected(Integer users_rejected) {
        this.users_rejected = users_rejected;
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

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Timestamp getDatetime_post() {
        return datetime_post;
    }

    public void setDatetime_post(Timestamp datetime_post) {
        this.datetime_post = datetime_post;
    }

    public Suggestion() {
    }

    public Suggestion(Long user_id, String title, String content, JSONArray media, JSONObject location, String place_id, Timestamp date_time_post) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.feed_media = media;
        this.location = location;
        this.place_id = place_id;
        this.datetime_post = date_time_post;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", title=" + title +
                ", content='" + content + '\'' +
                ", feed_media='" + feed_media + '\'' +
                ", location=" + location +
                ", place_id=" + place_id +
                ", date_time_post=" + datetime_post +
                '}';
    }
}
