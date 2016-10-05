package com.example.repository.votesRepository;

import com.example.entity.votes.VoteVoluntarie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteVoluntaryRepository extends JpaRepository<VoteVoluntarie,Long> {

    String JOINS_QUERY = "SELECT count(t.feed_id) FROM cl_vote_volutaries t WHERE t.feed_id = :feed_id";
    String USER_VOTE_STATUS = "SELECT count(1) from cl_vote_troubles t where t.feed_id = :feed_id AND t.user_id = :user_id";
    String DELETE_VOTE = "DELETE FROM cl_vote_volutaries t WHERE t.feed_id = :feed_id AND t.user_id = :user_id";


    @Query(value = JOINS_QUERY, nativeQuery = true)
    Integer getJoined(@Param("feed_id") Long feed_id);

    @Query(value = USER_VOTE_STATUS, nativeQuery = true)
    Integer getStatusVote(@Param("feed_id") Long feed_id, @Param("user_id") Long user_id);

    @Query(value = DELETE_VOTE, nativeQuery = true)
    void deleteVote(@Param("feed_id") Long feed_id, @Param("user_id") Long user_id);
}