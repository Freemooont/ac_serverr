package com.example.controllers.voteControllers;

import com.example.entity.votes.VoteEvent;
import com.example.repository.votesRepository.VoteEventRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/vote_event")
public class VoteEventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteEventController.class);


    VoteEventRepository voteEventRepository;

    @Autowired
    public VoteEventController(VoteEventRepository voteEventRepository) {
        this.voteEventRepository = voteEventRepository;
    }

    @RequestMapping(path = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    List<VoteEvent> getAll(){
        return voteEventRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    VoteEvent getById(@PathVariable Long id){
        return voteEventRepository.findOne(id);
    }

    @RequestMapping(path = "/insertVote", method = RequestMethod.POST)
    VoteEvent insertVote(@RequestBody VoteEvent voteEvent){
        LOGGER.debug("Creating {}", voteEvent);
        VoteEvent existing = voteEventRepository.findOne(voteEvent.getId());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", voteEvent.getId()));
        }
        return voteEventRepository.save(voteEvent);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void delete(@PathVariable Long id){
        voteEventRepository.delete(id);
    }

    @RequestMapping(path = "/getlikes/{feed_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getLikes(@PathVariable Long feed_id){

        JSONObject likes = new JSONObject();

        if (voteEventRepository.getInterested(feed_id) == null) {
            likes.put("likes",0);
            return likes;
        }else{
            likes.put("likes",voteEventRepository.getInterested(feed_id));
            return likes;
        }
    }

    @RequestMapping(path = "/getLikes/{feed_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getJoins(@PathVariable Long feed_id){

        JSONObject joins = new JSONObject();
        if(voteEventRepository.getJoined(feed_id) == null){
            joins.put("joins",0);
            return joins;
        }else {
            joins.put("joins", voteEventRepository.getJoined(feed_id) / 2);
            return joins;
        }
    }

    /*@RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    JsonArray getAllEventsWithLikes(){
        //System.out.println(voteEventRepository.getAllEventsLikes());

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        *//*System.out.println(gson.toJson(voteEventRepository.getAllEventsLikes()));*//*
       *//* JsonArray array = new JsonArray();
        array.add(gson.toJson(voteEventRepository.getAllEventsLikes()));*//*

       *//* JsonArray jsonArray = voteEventRepository.getAllEventsLikes();
        JsonArray main = new JsonArray();
        for(JsonElement object: jsonArray) {
            JsonArray array = object.getAsJsonArray();
            JsonObject item = new JsonObject();
            for (int i = 0; i < 2; i++) {
                JsonElement one = array.get(i);
                if (i == 0) {
                    item.add("feed_id", one);
                } else if (i == 1) {
                    item.add("likes", one);
                }
            }
            main.add(item);
        }*//*
        return array;
    }*/
}
