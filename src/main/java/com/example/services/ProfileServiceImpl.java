package com.example.services;

import com.example.entity.user.Profile;
import com.example.repository.userRepository.ProfileRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;


public class ProfileServiceImpl implements ProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);
    ProfileRepository profileRepository;

    @Inject
    public ProfileServiceImpl(ProfileRepository profileRepository)   {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile save(Profile profile) {
        LOGGER.debug("Creating {}", profile);
        Profile existing = profileRepository.findOne(profile.getId());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", profile.getId()));
        }
        return profileRepository.save(profile);
    }       

}
