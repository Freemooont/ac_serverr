package com.example.controllers.commentsControllers;

import com.example.entity.comments.*;
import com.example.repository.commentsRepository.EventsCommentsRepository;
import com.example.repository.commentsRepository.SuggestionsCommentsRepository;
import com.example.repository.commentsRepository.TroublesCommentsRepository;
import com.example.repository.commentsRepository.VoluntariesCommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentsController {
    EventsCommentsRepository eventsCommentsRepository;
    SuggestionsCommentsRepository suggestionsCommentsRepository;
    TroublesCommentsRepository troublesCommentsRepository;
    VoluntariesCommentsRepository voluntariesCommentsRepository;

    @Autowired
    public CommentsController(EventsCommentsRepository eventsCommentsRepository, SuggestionsCommentsRepository suggestionsCommentsRepository,
                              TroublesCommentsRepository troublesCommentsRepository, VoluntariesCommentsRepository voluntariesCommentsRepository) {
        this.eventsCommentsRepository = eventsCommentsRepository;
        this.suggestionsCommentsRepository = suggestionsCommentsRepository;
        this.troublesCommentsRepository = troublesCommentsRepository;
        this.voluntariesCommentsRepository = voluntariesCommentsRepository;
    }

    @RequestMapping(path = "/comments", method = RequestMethod.POST)
    @ResponseBody
    <T extends Comments>List<T> getCommetns(@RequestParam(value="feed_id", required=false) long feed_id,
                                            @RequestParam(value="feed_type", required=false) int feed_type,
                                            @RequestParam(value="user_id", required=false) long user_id,
                                            @RequestParam(value="nr_of_comments", required = false) int nr_of_comments,
                                            @RequestParam(value="index", required = false) int index){
        switch (feed_type){
            case 1:
                return (List<T>) voluntariesCommentsRepository.getVoluntaryComments(nr_of_comments,feed_id,user_id, index);
            case 2:
                return (List<T>) troublesCommentsRepository.getTroubleComments(nr_of_comments,feed_id,user_id, index);
            case 3:
                return (List<T>) suggestionsCommentsRepository.getSuggestionComments(nr_of_comments,feed_id,user_id, index);
            case 4:
                return (List<T>) eventsCommentsRepository.getEventComments(nr_of_comments, feed_id, user_id,index);
            default: return null;
        }
    }

    /*insert voluntary comment*/
    @RequestMapping(path = "comments/savecomment1", method = RequestMethod.POST)
    @ResponseBody
    VoluntariesComments saveComment(@RequestBody VoluntariesComments comment){
        return voluntariesCommentsRepository.save(comment);
    }

    /*insert trouble comment*/
    @RequestMapping(path = "comments/savecomment2", method = RequestMethod.POST)
    @ResponseBody
    TroublesComments saveComment(@RequestBody TroublesComments comment){
        return troublesCommentsRepository.save(comment);
    }

    /*insert suggestion comment*/
    @RequestMapping(path = "comments/savecomment3", method = RequestMethod.POST)
    @ResponseBody
    SuggestionComments saveComment(@RequestBody SuggestionComments comment){
        return suggestionsCommentsRepository.save(comment);
    }

    /*insert event comment*/
    @RequestMapping(path = "comments/savecomment4", method = RequestMethod.POST)
    @ResponseBody
    EventComments saveComment(@RequestBody EventComments comment){
        return eventsCommentsRepository.save(comment);
    }


}
