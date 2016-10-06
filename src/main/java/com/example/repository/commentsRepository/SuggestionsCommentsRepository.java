package com.example.repository.commentsRepository;

import com.example.entity.comments.EventComments;
import com.example.entity.comments.SuggestionComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SuggestionsCommentsRepository extends JpaRepository<SuggestionComments,Long> {
    String COMMENTS_BY_STEP = "select * from cl_suggestions_comments t where t.feed_id = :feed_id AND t.user_id = :user_id limit :nr_of_comments offset :offset ";

    @Query(value = COMMENTS_BY_STEP,nativeQuery = true)
    List<EventComments> getSuggestionComments(@Param("nr_of_comments") int nr_of_comments,
                                         @Param("feed_id") Long feed_id,
                                         @Param("user_id") Long user_id,
                                         @Param("offset") int index);

}
