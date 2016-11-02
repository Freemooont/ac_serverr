package com.example.repository.commentsRepository;

import com.example.entity.comments.EventComments;
import com.example.entity.comments.TroublesComments;
import com.example.entity.comments.VoluntariesComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VoluntariesCommentsRepository extends JpaRepository<VoluntariesComments,Long> {

    String COMMENTS_BY_STEP_WITH_USER = "select * from cl_voluntaries_comments t where t.feed_id = :feed_id AND t.user_id = :user_id limit :nr_of_comments offset :offset ";
    String COMMENTS_BY_STEP = "select * from cl_voluntaries_comments t where t.feed_id = :feed_id ORDER BY datetime_post DESC limit :nr_of_comments offset :offset ";
    String NUMBER_OF_COMMENTS = "select count(c.id) from cl_voluntaries_comments c where c.feed_id = :feed_id";


    @Query(value = COMMENTS_BY_STEP_WITH_USER,nativeQuery = true)
    List<VoluntariesComments> getVoluntaryComments(@Param("nr_of_comments") int nr_of_comments,
                                             @Param("feed_id") Long feed_id,
                                             @Param("user_id") Long user_id,
                                             @Param("offset") int index);

    @Query(value = COMMENTS_BY_STEP, nativeQuery = true)
    List<VoluntariesComments> getVoluntaryComments(@Param("nr_of_comments") int nr_of_comments,
                                              @Param("feed_id") Long feed_id,
                                              @Param("offset") int index);

    @Query(value = NUMBER_OF_COMMENTS, nativeQuery = true)
    Integer numberOfComments(@Param("feed_id") Long feed_id);

}
