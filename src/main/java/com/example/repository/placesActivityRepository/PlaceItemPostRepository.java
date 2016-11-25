package com.example.repository.placesActivityRepository;

import com.example.entity.feeds.Event;
import com.example.entity.palcesActivity.PlaceItemPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceItemPostRepository extends JpaRepository<PlaceItemPost, Long> {

    String PLACE_NUMBER = "select count(t.user_id) from cl_place_items_post t where t.user_id = :user_id";
    String PLACES_POST_BY_STEP = "select * from cl_place_items_post WHERE area_id in (:area_id) ORDER BY datetime_post DESC  limit :step offset :step_offset";
    String CURRENT_PLACE_POST = "SELECT * FROM cl_place_items_post e WHERE round(date_part( 'epoch', e.datetime_start)) * 1000  - :min_time < round(date_part( 'epoch', now())) * 1000  AND e.locality_id = :locality_id";

    @Query(value = PLACE_NUMBER, nativeQuery = true)
    Integer getNumberOfEventsPerUser(@Param("user_id") Long user_id);

    @Query(value = PLACES_POST_BY_STEP, nativeQuery = true)
    List<PlaceItemPost> returnByStep(@Param("step") int step, @Param("step_offset") int step_offset, @Param("area_id") Object[] area_id);

    @Query(value = CURRENT_PLACE_POST, nativeQuery = true)
    List<PlaceItemPost> getAllEvents(@Param("locality_id") String locality_id, @Param("min_time") Long min_time);

}
