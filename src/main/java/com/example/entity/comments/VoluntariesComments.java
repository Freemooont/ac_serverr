package com.example.entity.comments;

import com.example.entity.user.Profile;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_voluntaries_comments")
public class VoluntariesComments implements Comments{
    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    private Long id;

    @Column(name = "body",nullable =false)
    private String body;

    @Column(name = "user_id",nullable =false)
    private Long user_id;

    @Column(name = "feed_id",nullable =false)
    private Long feed_id;

    @Column(name = "datetime_post",nullable =false)
    private Timestamp datetime_post;

    @JoinColumn(name = "user_id",insertable=false, updatable=false)
    @ManyToOne
    Profile user;

    private transient int feed_type;

    public int getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(int feed_type) {
        this.feed_type = feed_type;
    }

    @JoinColumn(name = "user_id",insertable=false, updatable=false)
    @ManyToOne
    public Profile getUser() {
        return user;
    }

    @JoinColumn(name = "user_id",insertable=false, updatable=false)
    @ManyToOne
    public void setUser(Profile user) {
        this.user = user;
    }

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

    public Long getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(Long feed_id) {
        this.feed_id = feed_id;
    }

    public Timestamp getDatetime_post() {
        return datetime_post;
    }

    public void setDatetime_post(Timestamp datetime_post) {
        this.datetime_post = datetime_post;
    }

    public VoluntariesComments() {
    }

    public VoluntariesComments(String body,  Profile user, Long feed_id, Timestamp date_time) {
        this.body = body;
        this.user = user;
        this.user_id = user.getId();
        this.feed_id = feed_id;
        this.datetime_post = date_time;
    }

    @Override
    public String toString() {
        return "VoluntariesComments{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", user_id=" + user_id +
                ", feed_id=" + feed_id +
                ", datetime_post=" + datetime_post +
                '}';
    }
}
