package com.example.controllers.commentsControllers;

import com.example.entity.comments.EventComments;
import com.example.repository.commentsRepository.EventsCommentsRepository;
import com.example.repository.userRepository.ProfileRepository;
import com.example.repository.votesRepository.VoteEventRepository;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/eventcomments")
public class EventsCommentsController {
    EventsCommentsRepository eventsCommentsRepository;
    ProfileRepository profileRepository;
    VoteEventRepository voteEventRepository;

    @Autowired
    public EventsCommentsController(EventsCommentsRepository eventsCommentsRepository, ProfileRepository profileRepository,
                                    VoteEventRepository voteEventRepository) {
        this.eventsCommentsRepository = eventsCommentsRepository;
        this.profileRepository = profileRepository;
        this.voteEventRepository = voteEventRepository;
    }

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    List<EventComments> getAll(){
        return eventsCommentsRepository.findAll();
    }


    @RequestMapping(path = "/getmoreinfo",method = RequestMethod.GET)
    @ResponseBody
    Object getLastTen(){

        Map<String, Object> map = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        Object list1 =(Object) voteEventRepository.findAll();
        Object list2 = profileRepository.findAll();
        map.put("commentevent",list1);
        map.put("person",list2);
        System.out.println(map + "----------------------------------------------------------");
        return map;

    }



}
