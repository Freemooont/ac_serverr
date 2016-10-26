package com.example.controllers.feedControllers;

import com.example.entity.feeds.Event;
import com.example.repository.feedRepository.EventRepository;
import com.example.repository.votesRepository.VoteEventRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    EventRepository eventRepository;
    VoteEventRepository voteEventRepository;



    @Autowired
    public EventController(EventRepository eventRepository, VoteEventRepository voteEventRepository) {
        this.eventRepository = eventRepository;
        this.voteEventRepository = voteEventRepository;
    }

    @RequestMapping(path = "/getbyid/{id}",method = RequestMethod.GET)
    @ResponseBody
    Event getEvent(@PathVariable Long id){
        return eventRepository.findOne(id);
    }


    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    List<Event> getAll(){

        List<Event> list = new ArrayList<>();
        list = eventRepository.findAll();
        for(Event event:list){
           /* event.setUsers_supported(voteEventRepository.getInterested(event.getId()));
            event.setUsers_rejected((voteEventRepository.getJoined(event.getId()))/2);*/
        }
        return list;
    }

    @RequestMapping(path = "/event",method = RequestMethod.POST)
    Event insertEvent(@RequestBody Event event){
        LOGGER.debug("Creating {}", event);
        Event existing = eventRepository.findOne(event.getUser_id());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", event.getUser_id()));
        }
        return eventRepository.save(event);
    }


    /*step - cite feed-uri sa afiseze
    * step_offset - de la al citelea feed sa afiseze*/
    @RequestMapping(path = "/getbystep/{step}/{step_offset}",method = RequestMethod.GET)
    @ResponseBody
    List<Event> getByStep(@PathVariable int step, @PathVariable int step_offset){
        return eventRepository.returnByStep(step,step_offset);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void deleteEvent(@PathVariable Long id){
        eventRepository.delete(id);
    }

    @RequestMapping(path = "/nrfeeds/{user_id}", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getNumberOfEventsPerUser(@PathVariable(value = "user_id") Long user_id){
        JSONObject nrOfPublishedEvents = new JSONObject();
        if(eventRepository.getNumberOfEventsPerUser(user_id) == null){
            nrOfPublishedEvents.put("nrOfPublishedEvents",0);
            return nrOfPublishedEvents;
        }else{
            nrOfPublishedEvents.put("nrOfPublishedEvents",eventRepository.getNumberOfEventsPerUser(user_id));
            return nrOfPublishedEvents;
        }

    }
}
