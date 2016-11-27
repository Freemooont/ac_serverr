package com.example.controllers.userController;

import com.example.entity.user.Profile;
import com.example.entity.user.UserTokens;
import com.example.repository.userRepository.ProfileRepository;
import com.example.repository.userRepository.SettingsRepository;
import com.example.repository.userRepository.TokenRepository;
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
    TokenRepository tokenRepository;
    SettingsRepository settings;

    @Autowired
    public ProfileController(ProfileRepository profileRepository, TokenRepository tokenRepository,
                             SettingsRepository settings) {
        this.profileRepository = profileRepository;
        this.tokenRepository = tokenRepository;
        this.settings = settings;
    }

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    List<Profile> getAll() {
        return profileRepository.findAll();
    }

    @RequestMapping(path = "/getbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    Profile getOne(@PathVariable Long id) {
        return profileRepository.findOne(id);
    }


    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    Profile insert(@RequestBody Profile profile) {

        Profile existing = profileRepository.selectUserByFbId(profile.getFb_id());

        if (existing == null) {
            existing = profileRepository.save(profile);
        }

        String token = profile.getMobile_token();
        if (!token.equals("web")) {
            UserTokens user_token = tokenRepository.findToken(token);

            if (user_token == null) {
                user_token = new UserTokens(token, existing.getId());
                tokenRepository.save(user_token);
            } else {
                if (existing.getId() != user_token.getUser_id()) {
                    user_token.setUser_id(existing.getId());
                    tokenRepository.save(user_token);
                }
                existing.setMobile_token(String.valueOf(user_token.getId()));
            }

        }

        return existing;
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
    public void deleteProfile(@PathVariable Long id) {
        profileRepository.delete(id);
    }

    @RequestMapping(path = "/getUsersByStepByName", method = RequestMethod.GET)
    @ResponseBody
    List<Profile> getUsers(@RequestParam("str") String str,@RequestParam("index") int index, @RequestParam("count") int count){
        return profileRepository.selectUsers("%" +str+ "%",count, index);
    }


}
