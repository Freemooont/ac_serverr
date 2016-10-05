package com.example.controllers.feedControllers;

import com.example.entity.Profile;
import com.example.repository.feedRepository.ProfileRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class ProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    ProfileRepository profileRepository;

    @Autowired
    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @RequestMapping(path = "/getall", method = RequestMethod.GET )
    @ResponseBody
    List<Profile> getAll(){
        return profileRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}",method = RequestMethod.GET)
    @ResponseBody
    Profile getOne(@PathVariable Long id){
        return profileRepository.findOne(id);
    }

    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    Profile insert(@RequestBody Profile profile){

        Profile existing = profileRepository.slectUser(profile.getFb_id());

        if (existing != null) {
            if(!profile.getMobile_token().equals(existing.getMobile_token())) {
                existing.setMobile_token(profile.getMobile_token());
                return profileRepository.save(existing);
            }
           return existing;
        }
        profile.setSettings(new JSONObject());
        return profileRepository.save(profile);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public void deleteProfile(@PathVariable Long id){
        profileRepository.delete(id);
    }



}
