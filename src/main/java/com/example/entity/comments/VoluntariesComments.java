package com.example.entity.comments;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_voluntaries_comments")
public class VoluntariesComments {
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

    @Column(name = "date_time",nullable =false)
    private Timestamp date_time;

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

    public Timestamp getDate_time() {
        return date_time;
    }

    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
    }

    public VoluntariesComments() {
    }

    public VoluntariesComments(String body, Long user_id, Long feed_id, Timestamp date_time) {
        this.body = body;
        this.user_id = user_id;
        this.feed_id = feed_id;
        this.date_time = date_time;
    }

    @Override
    public String toString() {
        return "VoluntariesComments{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", user_id=" + user_id +
                ", feed_id=" + feed_id +
                ", date_time=" + date_time +
                '}';
    }
}
