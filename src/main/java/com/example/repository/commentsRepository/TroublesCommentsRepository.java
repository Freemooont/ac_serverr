package com.example.repository.commentsRepository;

import com.example.entity.comments.TroublesComments;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TroublesCommentsRepository extends JpaRepository<TroublesComments, Long>{
}
