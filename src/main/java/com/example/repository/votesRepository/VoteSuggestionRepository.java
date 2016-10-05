package com.example.repository.votesRepository;

import com.example.entity.votes.VoteSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteSuggestionRepository extends JpaRepository<VoteSuggestion , Long> {
    String REJECTED_QUERY = "SELECT count(t.vote) FROM cl_vote_suggestions t WHERE t.feed_id = :feed_id AND t.vote = 1 ";
    String SUPPORTED_QUERY = "SELECT count(t.vote) FROM cl_vote_suggestions t WHERE t.feed_id = :feed_id AND t.vote = 2 ";
    String USER_VOTE_STATUS = "SELECT t.vote from cl_vote_suggestions t WHERE t.feed_id = :feed_id AND t.user_id = :user_id ";


    @Query(value = REJECTED_QUERY,nativeQuery = true)
    Integer getRejected(@Param("feed_id") Long feed_id);

    @Query(value = SUPPORTED_QUERY, nativeQuery = true)
    Integer getSupported(@Param("feed_id") Long feed_id);

    @Query(value = USER_VOTE_STATUS, nativeQuery = true)
    Integer getStatusVote(@Param("feed_id") Long feed_id, @Param("user_id") Long user_id);
}
