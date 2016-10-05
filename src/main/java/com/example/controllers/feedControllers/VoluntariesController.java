package com.example.controllers.feedControllers;

import com.example.entity.feeds.Voluntaries;
import com.example.repository.feedRepository.VoluntariesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/voluntaries")
public class VoluntariesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoluntariesController.class);

    private VoluntariesRepository voluntariesRepository;

    @Autowired
    public VoluntariesController(VoluntariesRepository voluntariesRepository) {
        this.voluntariesRepository = voluntariesRepository;
    }

    @RequestMapping(path = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    List<Voluntaries> getAll(){
        return voluntariesRepository.findAll();
    }

    @RequestMapping(path = "/findone/{id}",method = RequestMethod.GET)
    @ResponseBody
    Voluntaries getVoluntary(@PathVariable Long id){
        return voluntariesRepository.findOne(id);
    }

    @RequestMapping(path = "/voluntary", method = RequestMethod.POST)
    public Voluntaries createVoluntary(@RequestBody Voluntaries voluntaries){
        LOGGER.debug("Creating {}", voluntaries);
        return voluntariesRepository.save(voluntaries);
    }

    /*@RequestMapping(path = "/voluntarytest", method = RequestMethod.POST)
    public Voluntaries createVoluntaryTest(@RequestBody Voluntaries voluntaries){
        LOGGER.debug("Creating {}", voluntaries);
        return voluntariesRepository.save(new Voluntaries((long)10,(long)12,"qwe","qwe","qwe","qwe","qwe",1472217868,1472217868,1472217868));
    }*/

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void deleteVoluntary(@PathVariable Long id){
        voluntariesRepository.delete(id);
    }

}
