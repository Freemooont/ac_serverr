package com.example.repository.commentsRepository;

import com.example.entity.comments.SuggestionComments;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SuggestionsCommentsRepository extends JpaRepository<SuggestionComments,Long> {
}
