package com.example.controllers.voteControllers;

import com.example.entity.votes.*;
import com.example.repository.votesRepository.VoteEventRepository;
import com.example.repository.votesRepository.VoteSuggestionRepository;
import com.example.repository.votesRepository.VoteTroubleRepository;
import com.example.repository.votesRepository.VoteVoluntaryRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoteController {

    VoteEventRepository voteEventRepository;
    VoteSuggestionRepository voteSuggestionRepository;
    VoteVoluntaryRepository voteVoluntaryRepository;
    VoteTroubleRepository voteTroubleRepository;

    @Autowired
    public VoteController(VoteEventRepository voteEventRepository, VoteSuggestionRepository voteSuggestionRepository, VoteVoluntaryRepository voteVoluntaryRepository, VoteTroubleRepository voteTroubleRepository) {
        this.voteEventRepository = voteEventRepository;
        this.voteSuggestionRepository = voteSuggestionRepository;
        this.voteVoluntaryRepository = voteVoluntaryRepository;
        this.voteTroubleRepository = voteTroubleRepository;
    }
    /*voting*/
    @RequestMapping(path = "/vote", method = RequestMethod.POST)
    @ResponseBody
    <T extends Vote> T voteSomething(@RequestParam(value="feed_id", required=false) long feed_id,
                                     @RequestParam(value="user_id", required=false) long user_id,
                                     @RequestParam(value="vote", required=false) int vote,
                                     @RequestParam(value="feed_type", required=false) int feed_type){
        switch (feed_type){
            case 1:
                if(vote == 1){
                    VoteVoluntarie voteVoluntarie =  new VoteVoluntarie();
                    voteVoluntarie.setFeedId(feed_id);
                    voteVoluntarie.setUserId(user_id);
                    return (T) voteVoluntaryRepository.save(voteVoluntarie);
                }else{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status","ok");
                    voteVoluntaryRepository.deleteVote(feed_id,user_id);
                    return (T) jsonObject;
                }

            case 2:
                VoteTrouble voteTrouble = new VoteTrouble();
                voteTrouble.setUserId(user_id);
                voteTrouble.setFeedId(feed_id);
                voteTrouble.setVote(vote);
                return (T) voteTroubleRepository.save(voteTrouble);
            case 3:
                VoteSuggestion voteSuggestion = new VoteSuggestion();
                voteSuggestion.setUserId(user_id);
                voteSuggestion.setFeedId(feed_id);
                voteSuggestion.setVote(vote);
                return (T)voteSuggestionRepository.save(voteSuggestion);
            case 4:
                VoteEvent voteEvent = new VoteEvent();
                voteEvent.setUserId(user_id);
                voteEvent.setFeedId(feed_id);
                voteEvent.setVote(vote);
                return (T) voteEventRepository.save(voteEvent);
            default:
        }
        return null;
    }
}
