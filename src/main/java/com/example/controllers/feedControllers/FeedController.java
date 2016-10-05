package com.example.controllers.feedControllers;

import com.example.entity.feeds.*;
import com.example.repository.feedRepository.EventRepository;
import com.example.repository.feedRepository.SuggestionRepository;
import com.example.repository.feedRepository.TroubleRepository;
import com.example.repository.feedRepository.VoluntariesRepository;
import com.example.repository.votesRepository.VoteEventRepository;
import com.example.repository.votesRepository.VoteSuggestionRepository;
import com.example.repository.votesRepository.VoteTroubleRepository;
import com.example.repository.votesRepository.VoteVoluntaryRepository;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedController {

    EventRepository eventRepository;
    SuggestionRepository suggestionRepository;
    TroubleRepository troubleRepository;
    VoluntariesRepository voluntariesRepository;
    VoteEventRepository voteEventRepository;
    VoteSuggestionRepository voteSuggestionRepository;
    VoteVoluntaryRepository voteVoluntaryRepository;
    VoteTroubleRepository voteTroubleRepository;

    @Autowired
    public FeedController(EventRepository eventRepository, SuggestionRepository suggestionRepository, TroubleRepository troubleRepository, VoluntariesRepository voluntariesRepository, VoteEventRepository voteEventRepository, VoteSuggestionRepository voteSuggestionRepository, VoteVoluntaryRepository voteVoluntaryRepository, VoteTroubleRepository voteTroubleRepository) {
        this.eventRepository = eventRepository;
        this.suggestionRepository = suggestionRepository;
        this.troubleRepository = troubleRepository;
        this.voluntariesRepository = voluntariesRepository;
        this.voteEventRepository = voteEventRepository;
        this.voteSuggestionRepository = voteSuggestionRepository;
        this.voteVoluntaryRepository = voteVoluntaryRepository;
        this.voteTroubleRepository = voteTroubleRepository;
    }




    @RequestMapping(path = "/getFeedByTypee", method = RequestMethod.POST)
    @ResponseBody
    <T extends Feed>List<T> getByType(@RequestParam(value="feed_type", required=false) int feed_type,
                                      @RequestParam(value="user_id", required=false) long user_id,
                                      @RequestParam(value="count", required=false) int count,
                                      @RequestParam(value="index", required=false) int index){
        switch (feed_type){
            case 1:
                return (List<T>) voluntariesRepository.returnByStep(count,index);
            case 2:
                return (List<T>) troubleRepository.returnByStep(count,index);
            case 3:
                return (List<T>) suggestionRepository.returnByStep(count,index);
            case 4:
                return (List<T>) eventRepository.returnByStep(count,index);
            default:
        }
        return null;
    }


    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    Object getAll(){
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(eventRepository.findAll().toString());
        jsonArray.add(voluntariesRepository.findAll().toString());
        jsonArray.add(troubleRepository.findAll().toString());
        jsonArray.add(suggestionRepository.findAll().toString());
        return jsonArray;
    }

    /********************************************************************/
    /*get events with step andd offset ( dela - pina la )*/
    /********************************************************************/
    @RequestMapping(path = "/getFeedByType4", method = RequestMethod.POST)
    @ResponseBody
    List<Event> getEVent(@RequestParam(value="user_id", required=false) long user_id,
                         @RequestParam(value="count", required=false) int count,
                         @RequestParam(value="index", required=false) int index ){
        List<Event> events = eventRepository.returnByStep(count, index);
        for(Event currentEvent:events){
            currentEvent.setUsers_interested(voteEventRepository.getInterested(currentEvent.getId()));
            currentEvent.setUsers_joined(voteEventRepository.getJoined(currentEvent.getId()));
            currentEvent.setVote_status(voteEventRepository.getStatusVote(currentEvent.getId(),user_id));
        }
        return events;
    }

    /********************************************************************/
    /*get suggestions with step andd offset ( dela - pina la )*/
    /********************************************************************/
    @RequestMapping(path = "/getFeedByType3", method = RequestMethod.POST)
    @ResponseBody
    List<Suggestion> getSugestion(@RequestParam(value="user_id", required=false) long user_id,
                         @RequestParam(value="count", required=false) int count,
                         @RequestParam(value="index", required=false) int index ){
        List<Suggestion> suggestions = suggestionRepository.returnByStep(count, index);
        for (Suggestion currentSuggestion: suggestions){
            currentSuggestion.setUsers_supported(voteSuggestionRepository.getSupported(currentSuggestion.getId()));
            currentSuggestion.setUsers_rejected(voteSuggestionRepository.getRejected(currentSuggestion.getId()));
            currentSuggestion.setVote_status(voteSuggestionRepository.getStatusVote(currentSuggestion.getId(),user_id));
        }
        return suggestions;
    }

    /********************************************************************/
    /*get troubles with step andd offset ( dela - pina la )*/
    /********************************************************************/
    @RequestMapping(path = "/getFeedByType2", method = RequestMethod.POST)
    @ResponseBody
    List<Trouble> getTroubles(@RequestParam(value="user_id", required=false) long user_id,
                                  @RequestParam(value="count", required=false) int count,
                                  @RequestParam(value="index", required=false) int index ){
        List<Trouble> troubles = troubleRepository.returnByStep(count,index);
        for(Trouble currentTrouble: troubles){
            currentTrouble.setUsers_supported(voteTroubleRepository.getSupported(currentTrouble.getId()));
            currentTrouble.setUsers_rejected(voteTroubleRepository.getRejected(currentTrouble.getId()));
            currentTrouble.setVote_status(voteTroubleRepository.getStatusVote(currentTrouble.getId(),user_id));
        }
        return troubles;
    }

    @RequestMapping(path = "/getFeedByType1", method = RequestMethod.POST)
    @ResponseBody
    List<Voluntaries> getVoluntaries(@RequestParam(value="user_id", required=false) Long user_id,
                              @RequestParam(value="count", required=false) int count,
                              @RequestParam(value="index", required=false) int index ){
        List<Voluntaries> voluntaries = voluntariesRepository.returnByStep(count, index);
        for(Voluntaries currentVoluntaries:voluntaries){
            currentVoluntaries.setUsers_joined(voteVoluntaryRepository.getJoined(currentVoluntaries.getId()));
            currentVoluntaries.setVote_status(voteVoluntaryRepository.getStatusVote(currentVoluntaries.getId(),user_id));
        }
        return voluntaries;
    }

    /********************************************************************/
    /*insert voluntaries*/
    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed1", method = RequestMethod.POST)
    @ResponseBody
    Voluntaries insertVountary(@RequestBody Voluntaries feed){
        return voluntariesRepository.save((Voluntaries) feed);
    }

    /********************************************************************/
    /*insert trouble*/
    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed2", method = RequestMethod.POST)
    @ResponseBody
    Trouble insertTrouble(@RequestBody Trouble feed){
        return troubleRepository.save(feed);
    }

    /********************************************************************/
    /*insert suggestion*/
    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed3", method = RequestMethod.POST)
    @ResponseBody
    Suggestion insertSuggestion(@RequestBody Suggestion feed){
        return suggestionRepository.save(feed);
    }
    /********************************************************************/
    /*insert event*/
    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed4", method = RequestMethod.POST)
    @ResponseBody
    Event insertEvent(@RequestBody Event feed){
        return eventRepository.save(feed);
    }
}
