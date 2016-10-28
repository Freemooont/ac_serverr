package com.example.entity.votes;

import javax.persistence.*;

@Entity
@Table(name = "cl_vote_voluntaries")
public class VoteVoluntarie implements Vote {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "user_id",nullable =false)
    Long userId;

    @Column(name = "feed_id",nullable =false)
    Long feedId;

    public VoteVoluntarie() {

    }

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

    public VoteVoluntarie(Long userId, Long feedId) {
        this.userId = userId;
        this.feedId = feedId;
    }

    @Override
    public String toString() {
        return "VoteVoluntarie{" +
                "id=" + id +
                ", userId=" + userId +
                ", feedId=" + feedId +
                '}';
    }
}
