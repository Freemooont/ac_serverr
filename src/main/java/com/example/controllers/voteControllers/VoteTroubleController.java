package com.example.controllers.voteControllers;

import com.example.entity.votes.VoteTrouble;
import com.example.repository.votesRepository.VoteTroubleRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/vote_trouble")
public class VoteTroubleController {
    VoteTroubleRepository voteTroubleRepository;

    @Autowired
    public VoteTroubleController(VoteTroubleRepository voteTroubleRepository) {
        this.voteTroubleRepository = voteTroubleRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteEventController.class);


    @RequestMapping(path = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    List<VoteTrouble> getAll(){
        return voteTroubleRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    VoteTrouble getById(@PathVariable Long id){
        return voteTroubleRepository.findOne(id);
    }

    @RequestMapping(path = "/insertVote", method = RequestMethod.POST)
    VoteTrouble insertVote(@RequestBody VoteTrouble voteTrouble){
        LOGGER.debug("Creating {}", voteTrouble);
        VoteTrouble existing = voteTroubleRepository.findOne(voteTrouble.getId());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", voteTrouble.getId()));
        }
        return voteTroubleRepository.save(voteTrouble);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void delete(@PathVariable Long id){
        voteTroubleRepository.delete(id);
    }

    @RequestMapping(path = "/getlikes/{feed_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getLikes(@PathVariable Long feed_id){

        JSONObject likes = new JSONObject();

        if (voteTroubleRepository.getSupported(feed_id) == null) {
            likes.put("likes",0);
            return likes;
        }else{
            likes.put("likes",voteTroubleRepository.getSupported(feed_id));
            return likes;
        }
    }

    @RequestMapping(path = "/getJoined/{feed_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getJoins(@PathVariable Long feed_id){

        JSONObject joins = new JSONObject();
        if(voteTroubleRepository.getRejected(feed_id) == null){
            joins.put("joins",0);
            return joins;
        }else {
            joins.put("joins", voteTroubleRepository.getRejected(feed_id) / 2);
            return joins;
        }
    }
}
