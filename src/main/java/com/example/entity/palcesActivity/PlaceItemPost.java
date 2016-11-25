package com.example.entity.palcesActivity;

import com.example.configs.JpaArrayConverter;
import com.example.configs.JpaConverterJson;
import com.example.controllers.placesActivity.PlaceItemValidator;
import com.example.entity.user.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_place_items_post")
public class PlaceItemPost implements PlaceItemCustom{
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

    @Column(name = "area_id",nullable =false)
    String area_id="123";

    @Column(name = "locality_id",nullable =false)
    String locality_id="123";

    @Column(name = "place_id", insertable = false, updatable = false)
    Long place_id;

    @Column(name = "datetime_post", nullable = false)
    Timestamp datetime_post;

    @Column(name = "datetime_start", nullable = false)
    Timestamp datetime_start;

    @Column(name = "datetime_end", nullable = false)
    Timestamp datetime_end;

    @JoinColumn(name = "place_id")
    @ManyToOne
    PlaceItem placeItem;

    public Integer getLikes() {
        return likes;
    }

    public void setPostLikes(Integer likes) {
        this.likes = likes;
    }

    transient Integer likes;

    transient Integer vote_status;

    transient Integer comments_count;

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

    public Long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Long place_id) {
        this.place_id = place_id;
    }

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

    public Timestamp getDatetime_end() {
        return datetime_end;
    }

    public void setDatetime_end(Timestamp datetime_end) {
        this.datetime_end = datetime_end;
    }

    public PlaceItem getPlaceItem() {
        return placeItem;
    }

    public void setPlaceItem(PlaceItem placeItem) {
        this.placeItem = placeItem;
    }

    public Integer getVote_status() {
        return vote_status;
    }

    public void setVote_status(Integer vote_status) {
        this.vote_status = vote_status;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public PlaceItemPost() {
    }
}