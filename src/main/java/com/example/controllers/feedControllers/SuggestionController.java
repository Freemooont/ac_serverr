package com.example.controllers.feedControllers;

import com.example.entity.feeds.Suggestion;
import com.example.repository.feedRepository.SuggestionRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/suggestions")
public class SuggestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionController.class);


    SuggestionRepository suggestionRepository;

    @Autowired
    public SuggestionController(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    List<Suggestion> getAll(){
        return suggestionRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    Suggestion getOne(@PathVariable Long id){
        return suggestionRepository.findOne(id);
    }

    @RequestMapping(path = "/suggestion",method = RequestMethod.POST)
    Suggestion insert(@RequestBody Suggestion suggestion){
        LOGGER.debug("Creating {}", suggestion);
        Suggestion existing = suggestionRepository.findOne(suggestion.getUser_id());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", suggestion.getUser_id()));
        }
        return suggestionRepository.save(suggestion);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
    public void deleteSuggestion(@PathVariable Long id){
        suggestionRepository.delete(id);
    }

}
