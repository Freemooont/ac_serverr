package com.example.repository.feedRepository;

import com.example.entity.feeds.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    String FEEDS_NUMBER = "select count(t.user_id) from cl_feed_events t where t.user_id = :user_id";
    String FEEDS_BY_STEP = "select * from cl_feed_events limit :step offset :step_offset";
    String FEEDS_BY_STEP1 = "select * from cl_feed_events limit :step offset :step_offset";

    @Query(value = FEEDS_NUMBER, nativeQuery = true)
    Integer getNumberOfEventsPerUser(@Param("user_id") Long user_id);

    @Query(value = FEEDS_BY_STEP, nativeQuery = true)
    List<Event> returnByStep(@Param("step") int step, @Param("step_offset") int step_offset);

    @Query(value = FEEDS_BY_STEP1, nativeQuery = true)
    List<Event> returnByStep1(@Param("step") int step, @Param("step_offset") int step_offset);
}
