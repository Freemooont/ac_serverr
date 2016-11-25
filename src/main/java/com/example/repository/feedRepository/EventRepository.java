package com.example.repository.feedRepository;

import com.example.entity.feeds.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.example.repository.feedRepository.EventRepository.CURRENT_FEEDS;

public interface EventRepository extends JpaRepository<Event,Long> {
    String FEEDS_NUMBER = "select count(t.user_id) from cl_feed_events t where t.user_id = :user_id";
    String FEEDS_BY_STEP = "select * from cl_feed_events WHERE area_id in (:area_id) ORDER BY datetime_post DESC  limit :step offset :step_offset";
    String CURRENT_FEEDS = "SELECT * FROM cl_feed_events e WHERE round(date_part( 'epoch', e.datetime_start)) * 1000  - :min_time < round(date_part( 'epoch', now())) * 1000  AND e.locality_id = :locality_id";

    @Query(value = FEEDS_NUMBER, nativeQuery = true)
    Integer getNumberOfEventsPerUser(@Param("user_id") Long user_id);

    @Query(value = FEEDS_BY_STEP, nativeQuery = true)
    List<Event> returnByStep(@Param("step") int step, @Param("step_offset") int step_offset, @Param("area_id") Object[] area_id);

    @Query(value = CURRENT_FEEDS, nativeQuery = true)
    List<Event> getAllEvents(@Param("locality_id") String locality_id, @Param("min_time") Long min_time);

}
