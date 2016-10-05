package com.example.services;


import com.example.entity.feeds.Voluntaries;
import com.example.repository.feedRepository.VoluntariesRepository;
import com.example.services.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
public class VoluntariesServiceImpl implements VoluntariesService{

    private static final Logger LOGGER = LoggerFactory.getLogger(VoluntariesServiceImpl.class);
    private final VoluntariesRepository voluntariesRepository;

    @Inject
    public VoluntariesServiceImpl(VoluntariesRepository voluntariesRepository) {
        this.voluntariesRepository = voluntariesRepository;
    }

    @Override
    @Transactional
    public Voluntaries save(Voluntaries voluntaries) {
        LOGGER.debug("Creating {}", voluntaries);
        Voluntaries existing = voluntariesRepository.findOne(voluntaries.getId());
        if (existing != null) {
            throw new UserAlreadyExistsException(
                    String.format("There already exists a user with id=%s", voluntaries.getId()));
        }
        return voluntariesRepository.save(voluntaries);
    }
}
