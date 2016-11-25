package com.example.repository.feedRepository;

import com.example.entity.feeds.Event;
import com.example.entity.feeds.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion,Long> {
    String FEEDS_BY_STEP = "select * from cl_feed_suggestions where area_id in (:area_id) ORDER BY date_time_post DESC limit :step offset :step_offset";
    String CURRENT_FEEDS = "SELECT * FROM cl_feed_suggestions e WHERE round(date_part( 'epoch', e.date_time_post)) * 1000  + e.notified_time > round(date_part( 'epoch', now())) * 1000  AND e.locality_id = :locality_id";
    @Query(value = FEEDS_BY_STEP, nativeQuery = true)
    List<Suggestion> returnByStep(@Param("step") int step, @Param("step_offset") int step_offset, @Param("area_id") Object[] area_id);

    @Query(value = CURRENT_FEEDS, nativeQuery = true)
    List<Suggestion> getAllSuggestion(@Param("locality_id") String locality_id);

}
