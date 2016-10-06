package com.example.controllers.voteControllers;

import com.example.entity.votes.*;
import com.example.repository.votesRepository.VoteEventRepository;
import com.example.repository.votesRepository.VoteSuggestionRepository;
import com.example.repository.votesRepository.VoteTroubleRepository;
import com.example.repository.votesRepository.VoteVoluntaryRepository;
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
    /*generic voting*/
    @RequestMapping(path = "/vote", method = RequestMethod.POST)
    @ResponseBody
    <T extends Vote> T voteSomething(@RequestParam(value="feed_id", required=false) long feed_id,
                                     @RequestParam(value="user_id", required=false) long user_id,
                                     @RequestParam(value="vote", required=false) int vote,
                                     @RequestParam(value="feed_type", required=false) int feed_type){
        switch (feed_type){
            case 1:
               VoteVoluntarie voteVoluntarie1 = null;
                if(vote==1){
                try{
                    voteVoluntarie1 = voteVoluntaryRepository.checkIfExist(feed_id,user_id);
                }catch (NullPointerException ignored){

                }finally {
                    if(voteVoluntarie1 == null) {
                        VoteVoluntarie voteVoluntarie =  new VoteVoluntarie();
                        voteVoluntarie.setFeedId(feed_id);
                        voteVoluntarie.setUserId(user_id);
                        return (T) voteVoluntaryRepository.save(voteVoluntarie);
                    }
                    else {
                            return (T)voteVoluntarie1;
                    }
                }
                }else if(vote==0){
                    voteVoluntaryRepository.deleteVote(feed_id,user_id);
                    return (T) new VoteVoluntarie();
                }

            case 2:
                VoteTrouble voteTrouble1 = null;
                try {
                    voteTrouble1 = voteTroubleRepository.checkIfExist(feed_id, user_id);
                }catch (NullPointerException ignored){
                        ignored.printStackTrace();
                } finally {


                if(voteTrouble1 ==null) {
                    VoteTrouble voteTrouble = new VoteTrouble();
                    voteTrouble.setUserId(user_id);
                    voteTrouble.setFeedId(feed_id);
                    voteTrouble.setVote(vote);
                    return (T) voteTroubleRepository.save(voteTrouble);
                }else{
                    voteTrouble1.setVote(vote);
                    return (T)voteTrouble1;
                }
                }

            case 3:
                VoteSuggestion voteSuggestion1 = null;
                try{
                    voteSuggestion1 = voteSuggestionRepository.checkIfExist(feed_id,user_id);
                }catch (NullPointerException ignored){
                    ignored.printStackTrace();
                }
                finally {

                if(voteSuggestion1==null) {
                    VoteSuggestion voteSuggestion = new VoteSuggestion();
                    voteSuggestion.setUserId(user_id);
                    voteSuggestion.setFeedId(feed_id);
                    voteSuggestion.setVote(vote);
                    return (T) voteSuggestionRepository.save(voteSuggestion);
                }else{
                    voteSuggestion1.setVote(vote);
                    return (T)voteSuggestion1;
                }
                }
            case 4:
                VoteEvent voteEvent1 = null;
                try{
                    voteEvent1 = voteEventRepository.checkIfExist(feed_id,user_id);
                }catch (NullPointerException ignored){
                    ignored.printStackTrace();
                }
                finally {

                if(voteEvent1 ==null){
                    VoteEvent voteEvent = new VoteEvent();
                    voteEvent.setUserId(user_id);
                    voteEvent.setFeedId(feed_id);
                    voteEvent.setVote(vote);
                    return (T) voteEventRepository.save(voteEvent);
                }else {
                    voteEvent1.setVote(vote);
                    return (T) voteEvent1;
                }
                }
            default:
        }
        return null;
    }
}
