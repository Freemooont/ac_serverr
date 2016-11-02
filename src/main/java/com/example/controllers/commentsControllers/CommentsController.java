package com.example.controllers.commentsControllers;

import com.example.entity.Profile;
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

    @RequestMapping(path = "/getFeedComments", method = RequestMethod.POST)
    @ResponseBody
    <T extends Comments> List<T> getCommetns(@RequestParam(value = "feed_id", required = false) long feed_id,
                                             @RequestParam(value = "feed_type", required = false) int feed_type,
                                             @RequestParam(value = "count", required = false) int count,
                                             @RequestParam(value = "index", required = false) int index) {
        Profile profile = new Profile();
        switch (feed_type) {
            case 1:
                return (List<T>) voluntariesCommentsRepository.getVoluntaryComments(count, feed_id, index);
            case 2:
                return (List<T>) troublesCommentsRepository.getTroubleComments(count, feed_id, index);
            case 3:
                return (List<T>) suggestionsCommentsRepository.getSuggestionComments(count, feed_id, index);
            case 4:
                return (List<T>) eventsCommentsRepository.getEventComments(count, feed_id, index);
            default:
                return null;
        }
    }

    /*insert voluntary comment*/
    @RequestMapping(path = "comments/savecomment1", method = RequestMethod.POST)
    @ResponseBody
    VoluntariesComments saveComment(@RequestBody VoluntariesComments comment) {
        return voluntariesCommentsRepository.save(comment);
    }

    /*insert trouble comment*/
    @RequestMapping(path = "comments/savecomment2", method = RequestMethod.POST)
    @ResponseBody
    TroublesComments saveComment(@RequestBody TroublesComments comment) {
        return troublesCommentsRepository.save(comment);
    }

    /*insert suggestion comment*/
    @RequestMapping(path = "comments/savecomment3", method = RequestMethod.POST)
    @ResponseBody
    SuggestionComments saveComment(@RequestBody SuggestionComments comment) {
        return suggestionsCommentsRepository.save(comment);
    }

    /*insert event comment*/
    @RequestMapping(path = "comments/savecomment4", method = RequestMethod.POST)
    @ResponseBody
    EventComments saveComment(@RequestBody EventComments comment) {
        return eventsCommentsRepository.save(comment);
    }


    @RequestMapping(path = "/setFeedComment", method = RequestMethod.POST)
    @ResponseBody
    private <T extends Comments> T insertComment(@RequestBody FeedComment comment) {
        Comments comments = comment.transform();

        if (comments instanceof VoluntariesComments) {
            return (T) voluntariesCommentsRepository.save((VoluntariesComments) comments);
        } else if (comments instanceof TroublesComments) {
            return (T) troublesCommentsRepository.save((TroublesComments) comments);
        } else if (comments instanceof SuggestionComments) {
            return (T) suggestionsCommentsRepository.save((SuggestionComments) comments);
        } else if (comments instanceof EventComments) {
            return (T) eventsCommentsRepository.save((EventComments) comments);
        } else
            return null;
    }


}
