package com.example.entity.votes;

import com.example.entity.feeds.Feed;

import javax.persistence.*;

@Entity
@Table(name = "cl_vote_place")
public class VotePlace implements Vote {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "user_id",nullable =false)
    Long userId;

    @Column(name = "post_id",nullable =false)
    Long post_id;

    @Column(name = "vote",nullable =false)
    int vote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public VotePlace(Long userId, Long feedId, int vote) {
        this.userId = userId;
        this.post_id = feedId;
        this.vote = vote;
    }

    public VotePlace() {
    }
}
