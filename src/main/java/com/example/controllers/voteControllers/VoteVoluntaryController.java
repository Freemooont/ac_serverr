package com.example.controllers.voteControllers;

import com.example.entity.votes.VoteVoluntarie;
import com.example.repository.votesRepository.VoteVoluntaryRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "/vote_voluntary")
public class VoteVoluntaryController{
    VoteVoluntaryRepository voteVoluntaryRepository;

    @Autowired
    public VoteVoluntaryController(VoteVoluntaryRepository voteVoluntaryRepository) {
        this.voteVoluntaryRepository = voteVoluntaryRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteEventController.class);


    @RequestMapping(path = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    List<VoteVoluntarie> getAll(){
        return voteVoluntaryRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    VoteVoluntarie getById(@PathVariable Long id){
        return voteVoluntaryRepository.findOne(id);
    }

    @RequestMapping(path = "/insertVote", method = RequestMethod.POST)
    VoteVoluntarie insertVote(@RequestBody VoteVoluntarie voteVoluntarie){
        LOGGER.debug("Creating {}", voteVoluntarie);
        VoteVoluntarie existing = voteVoluntaryRepository.findOne(voteVoluntarie.getId());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", voteVoluntarie.getId()));
        }
        return voteVoluntaryRepository.save(voteVoluntarie);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void delete(@PathVariable Long id){
        voteVoluntaryRepository.delete(id);
    }

    @RequestMapping(path = "/getJoined/{feed_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getJoins(@PathVariable Long feed_id){

        JSONObject joins = new JSONObject();
        if(voteVoluntaryRepository.getJoined(feed_id) == null){
            joins.put("joins",0);
            return joins;
        }else {
            joins.put("joins", voteVoluntaryRepository.getJoined(feed_id));
            return joins;
        }


    }


}
