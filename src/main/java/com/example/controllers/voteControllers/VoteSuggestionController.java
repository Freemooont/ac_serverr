package com.example.controllers.voteControllers;

import com.example.entity.votes.VoteSuggestion;
import com.example.repository.votesRepository.VoteSuggestionRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/vote_suggestion")
public class VoteSuggestionController {
    VoteSuggestionRepository voteSuggestionRepository;

    @Autowired
    public VoteSuggestionController(VoteSuggestionRepository voteSuggestionRepository) {
        this.voteSuggestionRepository = voteSuggestionRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteEventController.class);


    @RequestMapping(path = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    List<VoteSuggestion> getAll(){
        return voteSuggestionRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    VoteSuggestion getById(@PathVariable Long id){
        return voteSuggestionRepository.findOne(id);
    }

    @RequestMapping(path = "/insertVote", method = RequestMethod.POST)
    VoteSuggestion insertVote(@RequestBody VoteSuggestion voteSuggestion){
        LOGGER.debug("Creating {}", voteSuggestion);
        VoteSuggestion existing = voteSuggestionRepository.findOne(voteSuggestion.getId());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", voteSuggestion.getId()));
        }
        return voteSuggestionRepository.save(voteSuggestion);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void delete(@PathVariable Long id){
        voteSuggestionRepository.delete(id);
    }

    @RequestMapping(path = "/getlikes/{feed_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getLikes(@PathVariable Long feed_id){

        JSONObject likes = new JSONObject();

        if (voteSuggestionRepository.getRejected(feed_id) == null) {
            likes.put("likes",0);
            return likes;
        }else{
            likes.put("likes",voteSuggestionRepository.getRejected(feed_id));
            return likes;
        }
    }

    @RequestMapping(path = "/getLikes/{feed_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getJoins(@PathVariable Long feed_id){

        JSONObject joins = new JSONObject();
        if(voteSuggestionRepository.getSupported(feed_id) == null){
            joins.put("joins",0);
            return joins;
        }else {
            joins.put("joins", voteSuggestionRepository.getSupported(feed_id) / 2);
            return joins;
        }


    }
}
