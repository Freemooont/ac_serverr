package com.example.controllers.commentsControllers;

import com.example.entity.comments.SuggestionComments;
import com.example.repository.commentsRepository.SuggestionsCommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/suggestioncomments")
public class SuggestionsCommentsController {

    SuggestionsCommentsRepository suggestionsCommentsRepository;

    @Autowired
    public SuggestionsCommentsController(SuggestionsCommentsRepository suggestionsCommentsRepository) {
        this.suggestionsCommentsRepository = suggestionsCommentsRepository;
    }

    @RequestMapping(path = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    List<SuggestionComments> getAll(){
        return suggestionsCommentsRepository.findAll();
    }
}
