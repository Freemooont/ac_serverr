package com.example.controllers.feedControllers;

import com.example.entity.feeds.Trouble;
import com.example.repository.feedRepository.TroubleRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/troubles")
public class TroubleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TroubleController.class);

    TroubleRepository troubleRepository;

    @Autowired
    public TroubleController(TroubleRepository troubleRepository) {
        this.troubleRepository = troubleRepository;
    }

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    List<Trouble> getAll(){
        return troubleRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    Trouble getById(@PathVariable Long id){
        return troubleRepository.findOne(id);
    }

    @RequestMapping(path = "/trouble", method = RequestMethod.POST)
    @ResponseBody
    Trouble insert(@RequestBody Trouble trouble){
        LOGGER.debug("Creating {}", trouble);
        Trouble existing = troubleRepository.findOne(trouble.getUser_id());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", trouble.getUser_id()));
        }
        return troubleRepository.save(trouble);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void deleteTrounble(@PathVariable Long id){
        troubleRepository.delete(id);
    }
}
