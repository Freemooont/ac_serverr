package com.example.repository.votesRepository;

import com.example.entity.votes.EventLikes;
import com.example.entity.votes.VoteEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface VoteEventRepository extends JpaRepository <VoteEvent, Long> {
    String LIKES_QUERY = "SELECT count(t.vote) FROM cl_vote_events t WHERE t.feed_id = :feed_id AND t.vote = 1";
    String JOINS_QUERY = "SELECT count(t.vote) FROM cl_vote_events t WHERE t.feed_id = :feed_id AND t.vote = 2 ";
    String USER_VOTE_STATUS = "SELECT t.vote from cl_vote_events t WHERE t.feed_id = :feed_id AND t.user_id = :user_id ";

    @Query(value = LIKES_QUERY, nativeQuery = true)
    Integer getInterested(@Param("feed_id") Long feed_id);

    @Query(value = JOINS_QUERY, nativeQuery = true)
    Integer getJoined(@Param("feed_id") Long feed_id);

    @Query(value = USER_VOTE_STATUS, nativeQuery = true)
    Integer getStatusVote(@Param("feed_id") Long feed_id,@Param("user_id") Long user_id);


}
