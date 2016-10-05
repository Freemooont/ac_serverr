package com.example.repository.commentsRepository;

import com.example.entity.comments.VoluntariesComments;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VoluntariesCommentsRepository extends JpaRepository<VoluntariesComments,Long> {
}
