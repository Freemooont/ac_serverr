package com.example.entity.feeds;

import com.example.configs.JpaArrayConverter;
import com.example.configs.JpaConverterJson;
import com.example.entity.user.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_feed_troubles")
public class Trouble implements Feed{
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
    @Column(name = "feed_media",nullable =false, length = 5000)
    JSONArray feed_media;

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "location",nullable =false)
    JSONObject location;

    @Column(name = "area_id",nullable =false)
    String area_id="123";

    @Column(name = "locality_id",nullable =false)
    String locality_id="123";

    @Column(name = "date_time_post", nullable = false)
    Timestamp datetime_post;

    @JoinColumn(name = "user_id")
    @ManyToOne
    Profile user;

    transient Integer users_supported;

    transient Integer users_rejected;

    transient Integer vote_status;

    transient Integer comments_count;

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
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

    public Integer getVote_status() {
        return vote_status;
    }

    public void setVote_status(Integer vote_status) {
        this.vote_status = vote_status;
    }

    public JSONArray getFeed_media() {
        return feed_media;
    }

    public void setFeed_media(JSONArray feed_media) {
        this.feed_media = feed_media;
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

    public JSONObject getLocation() {
        return location;
    }

    public void setLocation(JSONObject location) {
        this.location = location;
    }

    public Timestamp getDatetime_post() {
        return datetime_post;
    }

    public void setDatetime_post(Timestamp datetime_post) {
        this.datetime_post = datetime_post;
    }

    public Trouble() {
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

    public Trouble(Long user_id, String title, String content, JSONArray feed_media, JSONObject location, String area_id, String locality_id, Timestamp datetime_post, Profile user, Integer users_supported, Integer users_rejected, Integer vote_status) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.feed_media = feed_media;
        this.location = location;
        this.area_id = area_id;
        this.locality_id = locality_id;
        this.datetime_post = datetime_post;
        this.user = user;
        this.users_supported = users_supported;
        this.users_rejected = users_rejected;
        this.vote_status = vote_status;
    }
}
