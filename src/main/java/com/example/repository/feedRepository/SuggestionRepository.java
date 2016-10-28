package com.example.repository.feedRepository;

import com.example.entity.feeds.Event;
import com.example.entity.feeds.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion,Long> {
    String FEEDS_BY_STEP = "select * from cl_feed_suggestions ORDER BY date_time_post DESC limit :step offset :step_offset";

    @Query(value = FEEDS_BY_STEP, nativeQuery = true)
    List<Suggestion> returnByStep(@Param("step") int step, @Param("step_offset") int step_offset);

}
