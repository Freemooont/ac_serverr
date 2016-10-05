package com.example.repository.commentsRepository;

import com.example.entity.comments.EventComments;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventsCommentsRepository extends JpaRepository<EventComments,Long> {

}
