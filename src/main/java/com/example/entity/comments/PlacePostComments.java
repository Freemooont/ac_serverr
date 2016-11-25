package com.example.entity.comments;

import com.example.entity.palcesActivity.PlaceItem;
import com.example.entity.user.Profile;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_place_post_comments")
public class PlacePostComments {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    private Long id;

    @Column(name = "body",nullable =false)
    private String body;

    @Column(name = "user_id",nullable =false, updatable = false, insertable = false)
    private Long user_id;

    @Column(name = "place_id", insertable = false, updatable = false)
    private Long place_id;

    @Column(name = "post_id",nullable =false)
    private Long post_id;

    @Column(name = "datetime_post",nullable =false)
    private Timestamp datetime_post;

    @JoinColumn(name = "user_id",insertable=false, updatable=false)
    @ManyToOne
    private Profile user;

    @JoinColumn(name = "place_id")
    @ManyToOne
    private PlaceItem placeItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Long place_id) {
        this.place_id = place_id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }


    public Timestamp getDatetime_post() {
        return datetime_post;
    }

    public void setDatetime_post(Timestamp datetime_post) {
        this.datetime_post = datetime_post;
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public PlaceItem getPlaceItem() {
        return placeItem;
    }

    public void setPlaceItem(PlaceItem placeItem) {
        this.placeItem = placeItem;
    }

    public PlacePostComments(String body, Long user_id, Long place_id,  Timestamp datetime_post, Profile user, PlaceItem placeItem) {
        this.body = body;
        this.user_id = user_id;
        this.place_id = place_id;

        this.datetime_post = datetime_post;
        this.user = user;
        this.placeItem = placeItem;
    }

    public PlacePostComments() {
    }
}
