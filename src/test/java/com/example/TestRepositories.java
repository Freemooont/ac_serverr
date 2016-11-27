package com.example;

import com.example.configs.DataBaseConfig;
import com.example.entity.user.Profile;
import com.example.repository.commentsRepository.EventsCommentsRepository;
import com.example.repository.commentsRepository.SuggestionsCommentsRepository;
import com.example.repository.commentsRepository.TroublesCommentsRepository;
import com.example.repository.commentsRepository.VoluntariesCommentsRepository;
import com.example.repository.feedRepository.*;
import com.example.repository.upload.UploadRepository;
import com.example.repository.userRepository.ProfileRepository;
import com.example.repository.userRepository.SettingsRepository;
import com.example.repository.userRepository.TokenRepository;
import com.example.repository.votesRepository.VoteEventRepository;
import com.example.repository.votesRepository.VoteSuggestionRepository;
import com.example.repository.votesRepository.VoteTroubleRepository;
import com.example.repository.votesRepository.VoteVoluntaryRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.matchers.JUnitMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataBaseConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})

public class TestRepositories {

    private EventRepository eventRepository;
    private SuggestionRepository suggestionRepository;
    private TroubleRepository troubleRepository;
    private VoluntariesRepository voluntariesRepository;
    private VoteEventRepository voteEventRepository;
    private VoteSuggestionRepository voteSuggestionRepository;
    private VoteVoluntaryRepository voteVoluntaryRepository;
    private VoteTroubleRepository voteTroubleRepository;
    private UploadRepository uploadRepository;
    private EventsCommentsRepository eventsCommentsRepository;
    private TroublesCommentsRepository troublesCommentsRepository;
    private VoluntariesCommentsRepository voluntariesCommentsRepository;
    private NotifyRepository notifyRepository;
    private TokenRepository tokenRepository;
    private SettingsRepository settingsRepository;
    private SuggestionsCommentsRepository suggestionsCommentsRepository;
    private ProfileRepository profileRepository;

    public TestRepositories(EventRepository eventRepository, SuggestionRepository suggestionRepository, TroubleRepository troubleRepository, VoluntariesRepository voluntariesRepository, VoteEventRepository voteEventRepository, VoteSuggestionRepository voteSuggestionRepository, VoteVoluntaryRepository voteVoluntaryRepository, VoteTroubleRepository voteTroubleRepository, UploadRepository uploadRepository, EventsCommentsRepository eventsCommentsRepository, TroublesCommentsRepository troublesCommentsRepository, VoluntariesCommentsRepository voluntariesCommentsRepository, NotifyRepository notifyRepository, TokenRepository tokenRepository, SettingsRepository settingsRepository, SuggestionsCommentsRepository suggestionsCommentsRepository, ProfileRepository profileRepository) {
        this.eventRepository = eventRepository;
        this.suggestionRepository = suggestionRepository;
        this.troubleRepository = troubleRepository;
        this.voluntariesRepository = voluntariesRepository;
        this.voteEventRepository = voteEventRepository;
        this.voteSuggestionRepository = voteSuggestionRepository;
        this.voteVoluntaryRepository = voteVoluntaryRepository;
        this.voteTroubleRepository = voteTroubleRepository;
        this.uploadRepository = uploadRepository;
        this.eventsCommentsRepository = eventsCommentsRepository;
        this.troublesCommentsRepository = troublesCommentsRepository;
        this.voluntariesCommentsRepository = voluntariesCommentsRepository;
        this.notifyRepository = notifyRepository;
        this.tokenRepository = tokenRepository;
        this.settingsRepository = settingsRepository;
        this.suggestionsCommentsRepository = suggestionsCommentsRepository;
        this.profileRepository = profileRepository;
    }

    Profile profile = new Profile(1l, "Nichita", "Tcaci", 1254127204599768l, new JSONObject(), "https://graph.facebook.com/1254127204599768/picture?height=480&width=480&migration_overrides=%7Boctober_2012%3Atrue%7D");
    Profile profile1 = new Profile(2l, "Igor", "Leahu", 1344127204293423l, new JSONObject(), "https://graph.facebook.com/1344127204293423/picture?height=480&width=480&migration_overrides=%7Boctober_2012%3Atrue%7D");

    @Before
    public void setUp() throws Exception {
        profileRepository.save(profile);
        profileRepository.save(profile1);
    }

    @After
    public void tearDown() throws Exception {
        profileRepository.delete(profile);
        profileRepository.delete(profile1);
    }

    @Test
    public void searchNotFoundEntries_shouldReturnEmptyList() {
        List<Profile> profiles = profileRepository.selectUserByLastName("NOT FOUND");
        Assert.assertThat(profiles.size(), is(0));
    }

    @Test
    public void searchProfileEntry_shouldReturnAProfile() {
        Profile profile = profileRepository.selectUserByFbId(1254127204599768l);
        assertThat(profile, allOf(
                hasProperty("id", is(1L)),
                hasProperty("first_name", is("Nichita")),
                hasProperty("last_name", is("Tcaci")),
                hasProperty("fb_id", is(1254127204599768l)),
                hasProperty("settings", is(new JSONObject())),
                hasProperty("avatar", is("https://graph.facebook.com/1254127204599768/picture?height=480&width=480&migration_overrides=%7Boctober_2012%3Atrue%7D"))
        ));
    }

    @Test
    public void searchProfileEntries_shouldReturnListOfProfiles() {
        List<Profile> profiles = profileRepository.selectUsers("c", 0, 100);
        assertThat(profiles.size(), is(2));
        assertThat(profiles, contains(
                allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("first_name", is("Nichita")),
                        hasProperty("last_name", is("Tcaci")),
                        hasProperty("fb_id", is(1254127204599768l)),
                        hasProperty("settings", is(new JSONObject())),
                        hasProperty("avatar", is("https://graph.facebook.com/1254127204599768/picture?height=480&width=480&migration_overrides=%7Boctober_2012%3Atrue%7D"))

                ),
                allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("first_name", is("Igor")),
                        hasProperty("last_name", is("Leahu")),
                        hasProperty("fb_id", is(1344127204293423l)),
                        hasProperty("settings", is(new JSONObject())),
                        hasProperty("avatar", is("https://graph.facebook.com/1344127204293423/picture?height=480&width=480&migration_overrides=%7Boctober_2012%3Atrue%7D"))
                )
        ));

    }

}
