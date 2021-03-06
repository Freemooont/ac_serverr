package com.example.repository.feedRepository;

import com.example.entity.feeds.Event;
import com.example.entity.feeds.Voluntaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoluntariesRepository extends JpaRepository<Voluntaries,Long> {

    String FEEDS_BY_STEP = "select * from cl_feed_voluntaries ORDER BY datetime_post DESC limit :step offset :step_offset";

    @Query(value = FEEDS_BY_STEP, nativeQuery = true)
    List<Voluntaries> returnByStep(@Param("step") int step, @Param("step_offset") int step_offset);
}
