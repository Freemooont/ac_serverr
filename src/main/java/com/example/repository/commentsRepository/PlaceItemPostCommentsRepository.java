package com.example.repository.commentsRepository;


import com.example.entity.comments.EventComments;
import com.example.entity.comments.PlacePostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaceItemPostCommentsRepository extends JpaRepository<PlacePostComments, Long> {
    String COMMENTS_BY_STEP_WITH_USER = "select * from cl_place_post_comments t where t.post_id = :post_id AND t.user_id = :user_id limit :nr_of_comments offset :offset ";
    String COMMENTS_BY_STEP = "select * from cl_place_post_comments t where t.post_id = :post_id ORDER BY datetime_post DESC limit :nr_of_comments offset :offset ";
    String NUMBER_OF_COMMENTS = "select count(c.id) from cl_place_post_comments c where c.post_id = :post_id";

    @Query(value = COMMENTS_BY_STEP_WITH_USER,nativeQuery = true)
    List<EventComments> getPlaceItemComments(@Param("nr_of_comments") int nr_of_comments,
                                         @Param("post_id") Long post_id,
                                         @Param("user_id") Long user_id,
                                         @Param("offset") int index);

    @Query(value = COMMENTS_BY_STEP, nativeQuery = true)
    List<EventComments> getPlaceItemComments(@Param("nr_of_comments") int nr_of_comments,
                                         @Param("post_id") Long post_id,
                                         @Param("offset") int index);

    @Transactional
    @Query(value = NUMBER_OF_COMMENTS, nativeQuery = true)
    Integer numberOfComments(@Param("post_id") Long post_id);
}
