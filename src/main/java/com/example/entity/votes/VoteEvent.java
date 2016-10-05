package com.example.entity.votes;

import javax.persistence.*;

@Entity
@Table(name = "cl_vote_events", schema = "public")
public class VoteEvent implements Vote {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "user_id",nullable =false)
    Long userId;

    @Column(name = "feed_id",nullable =false)
    Long feedId;

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

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public VoteEvent() {
    }

    public VoteEvent(Long userId, Long feedId, int vote) {
        this.userId = userId;
        this.feedId = feedId;
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "VoteEvent{" +
                "id=" + id +
                ", userId=" + userId +
                ", feedId=" + feedId +
                ", vote=" + vote +
                '}';
    }
}
