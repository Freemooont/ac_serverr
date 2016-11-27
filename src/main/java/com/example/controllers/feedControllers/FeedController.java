package com.example.controllers.feedControllers;

import com.example.UtilConstants;
import com.example.configs.MoveFile;
import com.example.dto.*;
import com.example.entity.feeds.*;
import com.example.entity.upload.Upload;
import com.example.repository.commentsRepository.EventsCommentsRepository;
import com.example.repository.commentsRepository.SuggestionsCommentsRepository;
import com.example.repository.commentsRepository.TroublesCommentsRepository;
import com.example.repository.commentsRepository.VoluntariesCommentsRepository;
import com.example.repository.feedRepository.*;
import com.example.repository.upload.UploadRepository;
import com.example.repository.userRepository.SettingsRepository;
import com.example.repository.userRepository.TokenRepository;
import com.example.repository.votesRepository.VoteEventRepository;
import com.example.repository.votesRepository.VoteSuggestionRepository;
import com.example.repository.votesRepository.VoteTroubleRepository;
import com.example.repository.votesRepository.VoteVoluntaryRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class FeedController {

    EventRepository eventRepository;
    SuggestionRepository suggestionRepository;
    TroubleRepository troubleRepository;
    VoluntariesRepository voluntariesRepository;
    VoteEventRepository voteEventRepository;
    VoteSuggestionRepository voteSuggestionRepository;
    VoteVoluntaryRepository voteVoluntaryRepository;
    VoteTroubleRepository voteTroubleRepository;
    UploadRepository uploadRepository;
    EventsCommentsRepository eventsCommentsRepository;
    TroublesCommentsRepository troublesCommentsRepository;
    VoluntariesCommentsRepository voluntariesCommentsRepository;
    NotifyRepository notifyRepository;
    TokenRepository tokenRepository;
    private SettingsRepository settingsRepository;
    private SuggestionsCommentsRepository suggestionsCommentsRepository;


    public static String STORAGE_PATH = "/opt/tomcat/storage/";


    @Autowired
    public FeedController(EventRepository eventRepository,
                          SuggestionRepository suggestionRepository,
                          TroubleRepository troubleRepository,
                          VoluntariesRepository voluntariesRepository,
                          VoteEventRepository voteEventRepository,
                          VoteSuggestionRepository voteSuggestionRepository,
                          VoteVoluntaryRepository voteVoluntaryRepository,
                          VoteTroubleRepository voteTroubleRepository,
                          UploadRepository uploadRepository,
                          EventsCommentsRepository eventsCommentsRepository,
                          TroublesCommentsRepository troublesCommentsRepository,
                          VoluntariesCommentsRepository voluntariesCommentsRepository,
                          SuggestionsCommentsRepository suggestionsCommentsRepository,
                          NotifyRepository notifyRepository,
                          TokenRepository tokenRepository,
                          SettingsRepository settingsRepository) {
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
        this.suggestionsCommentsRepository = suggestionsCommentsRepository;
        this.notifyRepository = notifyRepository;
        this.tokenRepository = tokenRepository;
        this.settingsRepository = settingsRepository;
    }


    @RequestMapping(path = "/getFeedById", method = RequestMethod.POST)
    @ResponseBody
    <T extends Feed> T getFeedById(@RequestParam("feed_id") Long id, @RequestParam("feed_type") int type) {
        switch (type) {
            case 1:
                return (T) voluntariesRepository.findOne(id);
            case 2:
                Trouble trouble = troubleRepository.findOne(id);
                trouble.setUsers_supported(voteTroubleRepository.getSupported(trouble.getId()));
                trouble.setUsers_rejected(voteTroubleRepository.getRejected(trouble.getId()));
                trouble.setVote_status(voteTroubleRepository.getStatusVote(trouble.getId(), trouble.getUser_id()));
                trouble.setComments_count((troublesCommentsRepository.numberOfComments(trouble.getId())));
                return (T) trouble;
            case 3:
                Suggestion suggestion = suggestionRepository.findOne(id);
                suggestion.setUsers_supported(voteSuggestionRepository.getSupported(suggestion.getId()));
                suggestion.setUsers_rejected(voteSuggestionRepository.getRejected(suggestion.getId()));
                suggestion.setVote_status(voteSuggestionRepository.getStatusVote(suggestion.getId(), suggestion.getUser_id()));
                suggestion.setComments_count((suggestionsCommentsRepository.numberOfComments(suggestion.getId())));
                return (T) suggestion;
            case 4:
                Event event = eventRepository.findOne(id);
                event.setUsers_interested(voteEventRepository.getInterested(event.getId()));
                event.setPostLikes(voteEventRepository.getJoined(event.getId()));
                event.setVote_status(voteEventRepository.getStatusVote(event.getId(), event.getUser_id()));
                event.setComments_count(eventsCommentsRepository.numberOfComments(event.getId()));
                return (T) event;
            default:
                return (T) new Object();

        }
    }

    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    Object getAll() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(eventRepository.findAll().toString());
        jsonArray.add(voluntariesRepository.findAll().toString());
        jsonArray.add(troubleRepository.findAll().toString());
        jsonArray.add(suggestionRepository.findAll().toString());
        return jsonArray;
    }

    /********************************************************************/
    /*get events with step andd offset ( dela - pina la )*/

    /********************************************************************/
    @RequestMapping(path = "/getFeedByType4", method = RequestMethod.POST)
    @ResponseBody
    List<Event> getEvent(@RequestParam(value = "user_id", required = false) long user_id,
                         @RequestParam(value = "count", required = false) int count,
                         @RequestParam(value = "index", required = false) int index) {
        List<String> place_ids = settingsRepository.getPlacesIds(user_id);
        if (place_ids != null && place_ids.size() > 0) {

            List<Event> events = eventRepository.returnByStep(count, index, place_ids.toArray());
            for (Event currentEvent : events) {
                currentEvent.setUsers_interested(voteEventRepository.getInterested(currentEvent.getId()));
                currentEvent.setPostLikes(voteEventRepository.getJoined(currentEvent.getId()));
                currentEvent.setVote_status(voteEventRepository.getStatusVote(currentEvent.getId(), user_id));
                currentEvent.setComments_count(eventsCommentsRepository.numberOfComments(currentEvent.getId()));
            }
            return events;
        } else
            return new ArrayList<>();


    }

    /********************************************************************/
    /*get suggestions with step andd offset ( dela - pina la )*/

    /********************************************************************/
    @RequestMapping(path = "/getFeedByType3", method = RequestMethod.POST)
    @ResponseBody
    List<Suggestion> getSugestion(@RequestParam(value = "user_id", required = false) long user_id,
                                  @RequestParam(value = "count", required = false) int count,
                                  @RequestParam(value = "index", required = false) int index) {
        List<String> place_ids = settingsRepository.getPlacesIds(user_id);
        if (place_ids != null && place_ids.size() > 0) {
            List<Suggestion> suggestions = suggestionRepository.returnByStep(count, index, place_ids.toArray());
            for (Suggestion currentSuggestion : suggestions) {
                currentSuggestion.setUsers_supported(voteSuggestionRepository.getSupported(currentSuggestion.getId()));
                currentSuggestion.setUsers_rejected(voteSuggestionRepository.getRejected(currentSuggestion.getId()));
                currentSuggestion.setVote_status(voteSuggestionRepository.getStatusVote(currentSuggestion.getId(), user_id));
                currentSuggestion.setComments_count((suggestionsCommentsRepository.numberOfComments(currentSuggestion.getId())));
            }
            return suggestions;
        } else
            return new ArrayList<>();
    }

    /********************************************************************/
    /*get troubles with step andd offset ( dela - pina la )*/

    /********************************************************************/
    @RequestMapping(path = "/getFeedByType2", method = RequestMethod.POST)
    @ResponseBody
    List<Trouble> getTroubles(@RequestParam(value = "user_id", required = false) long user_id,
                              @RequestParam(value = "count", required = false) int count,
                              @RequestParam(value = "index", required = false) int index) {
        List<String> place_ids = settingsRepository.getPlacesIds(user_id);
        if (place_ids != null && place_ids.size() > 0) {
            List<Trouble> troubles = troubleRepository.returnByStep(count, index, place_ids.toArray());
            for (Trouble currentTrouble : troubles) {
                currentTrouble.setUsers_supported(voteTroubleRepository.getSupported(currentTrouble.getId()));
                currentTrouble.setUsers_rejected(voteTroubleRepository.getRejected(currentTrouble.getId()));
                currentTrouble.setVote_status(voteTroubleRepository.getStatusVote(currentTrouble.getId(), user_id));
                currentTrouble.setComments_count((troublesCommentsRepository.numberOfComments(currentTrouble.getId())));
            }
            return troubles;
        } else return new ArrayList<>();

    }

    @RequestMapping(path = "/getFeedByType1", method = RequestMethod.POST)
    @ResponseBody
    List<Voluntaries> getVoluntaries(@RequestParam(value = "user_id", required = false) Long user_id,
                                     @RequestParam(value = "count", required = false) int count,
                                     @RequestParam(value = "index", required = false) int index) {
        List<Voluntaries> voluntaries = voluntariesRepository.returnByStep(count, index);
        System.out.println("Catalina home--------------------------------------" + System.getProperty("catalina.base").toString() + "    " + System.getProperty("catalina.home"));

        for (Voluntaries currentVoluntaries : voluntaries) {
            currentVoluntaries.setUsers_joined(voteVoluntaryRepository.getJoined(currentVoluntaries.getId()));
            currentVoluntaries.setVote_status(voteVoluntaryRepository.getStatusVote(currentVoluntaries.getId(), user_id));
            currentVoluntaries.setComments_count(voluntariesCommentsRepository.numberOfComments(currentVoluntaries.getId()));
        }
        return voluntaries;
    }


    /********************************************************************/
    /*insert trouble*/

    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed2", method = RequestMethod.POST)
    @ResponseBody
    JSONObject insertTrouble(@RequestBody Trouble feed, @RequestParam("token_id") long token) throws IOException {
        if (!FeedValidator.validate(feed)) {
            return UtilConstants.getResponseStatus(UtilConstants.INVALID_CONTENT);
        } else {
            JSONArray jsonElements = feed.getFeed_media();
            JsonArray elements = new JsonParser().parse(jsonElements.toString()).getAsJsonArray();
            JSONArray feed_media = new JSONArray();

            for (int i = 0; i < jsonElements.size(); i++) {
                Upload model = uploadRepository.findOne(elements.get(i).getAsJsonObject().get("id").getAsLong());
                if (model != null) {
                    if (model.getMedia_type() == 1) {

                        if (MoveFile.moveFile(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath())) {
                            System.out.println(STORAGE_PATH + "temp_media/" + model.getPath());
                            System.out.println(STORAGE_PATH + "photos/" + model.getPath());
                            JSONObject media = new JSONObject();
                            media.put("media_name", model.getPath());
                            media.put("media_type", model.getMedia_type());
                            feed_media.add(media);
                            uploadRepository.delete(model.getId());
                        } else {
                            MoveFile.moveFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                            JSONObject media = new JSONObject();
                            media.put("media_name", model.getPath());
                            media.put("media_type", model.getMedia_type());
                            feed_media.add(media);
                            uploadRepository.delete(model.getId());
                        }


                    } else {
                        if (MoveFile.moveFile(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath())) {
                            MoveFile.moveFile(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                        } else {

                            MoveFile.moveFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "videos/" + model.getPath());
                            JSONObject media = new JSONObject();
                            media.put("media_name", model.getPath());
                            media.put("media_type", model.getMedia_type());
                            feed_media.add(media);
                            uploadRepository.delete(model.getId());
                        }
                    }

                }
            }

            if (feed_media.size() > 0) {
                feed.setFeed_media(feed_media);
            } else feed.setFeed_media(new JSONArray());
            if (feed_media.size() > 0) {
                feed.setFeed_media(feed_media);
            } else feed.setFeed_media(new JSONArray());

            Location location = new Gson().fromJson(feed.getLocation().toJSONString(), Location.class);
            Response response = PlaceIdClient.maps(location.getCoordinates());
            if (response == null) {
                feed.setLocality_id("unavailable");
                feed.setArea_id("unavailable");
            } else {
                String message = response.body().string();
                Places placeInfo = new Gson().fromJson(message, Places.class);

                if (placeInfo.getResults().size() >= 1) {
                    PlaceInfo result_locality = placeInfo.getResults().get(0);
                    PlaceInfo result_area = placeInfo.getResults().get(1);

                    feed.setLocality_id(result_locality.place_id);
                    feed.setArea_id(result_area.place_id);
                } else {
                    feed.setLocality_id("unavailable");
                    feed.setArea_id("unavailable");
                }
            }

            feed = troubleRepository.save(feed);
            PushNotification.push(feed, PushNotification.TYPE_FEED_NOTIFICATION);
            PushNotification.pushToUser(feed, tokenRepository, token);
            return UtilConstants.getResponseStatus(UtilConstants.OK);

        }
    }

    /********************************************************************/
    /*insert suggestion*/

    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed3", method = RequestMethod.POST)
    @ResponseBody
    JSONObject insertSuggestion(@RequestBody Suggestion feed, @RequestParam("token_id") long token) throws
            IOException {
        if (!FeedValidator.validate(feed)) {
            return UtilConstants.getResponseStatus(UtilConstants.INVALID_CONTENT);
        } else {
            JSONArray jsonElements = feed.getFeed_media();
            JsonArray elements = new JsonParser().parse(jsonElements.toString()).getAsJsonArray();
            JSONArray feed_media = new JSONArray();

            for (int i = 0; i < jsonElements.size(); i++) {
                Upload model = uploadRepository.findOne(elements.get(i).getAsJsonObject().get("id").getAsLong());
                if (model != null) {
                    if (model.getMedia_type() == 1) {

                        MoveFile.moveFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                        JSONObject media = new JSONObject();

                        media.put("media_name", model.getPath());
                        media.put("media_type", model.getMedia_type());
                        feed_media.add(media);
                        uploadRepository.delete(model.getId());


                    } else {
                        MoveFile.moveFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "videos/" + model.getPath());
                        JSONObject media = new JSONObject();
                        media.put("media_name", model.getPath());
                        media.put("media_type", model.getMedia_type());
                        feed_media.add(media);
                        uploadRepository.delete(model.getId());
                    }

                }
            }

            if (feed_media.size() > 0) {
                feed.setFeed_media(feed_media);
            } else feed.setFeed_media(new JSONArray());

            if (feed_media.size() > 0) {
                feed.setFeed_media(feed_media);
            } else feed.setFeed_media(new JSONArray());

            Location location = new Gson().fromJson(feed.getLocation().toJSONString(), Location.class);
            Response response = PlaceIdClient.maps(location.getCoordinates());
            if (response == null) {
                feed.setLocality_id("unavailable");
                feed.setArea_id("unavailable");
            } else {
                String message = new String(response.body().bytes());
                System.out.println("code" + response.code());
                System.out.println("codewww" + message);
                Places placeInfo = new Gson().fromJson(message, Places.class);

                if (placeInfo.getResults().size() >= 1) {
                    PlaceInfo result_locality = placeInfo.getResults().get(0);
                    PlaceInfo result_area = placeInfo.getResults().get(1);

                    feed.setLocality_id(result_locality.place_id);
                    feed.setArea_id(result_area.place_id);
                } else {
                    feed.setLocality_id("unavailable");
                    feed.setArea_id("unavailable");
                }
            }

            feed = suggestionRepository.save(feed);
            PushNotification.push(feed, PushNotification.TYPE_FEED_NOTIFICATION);
            PushNotification.pushToUser(feed, tokenRepository, token);
            return UtilConstants.getResponseStatus(UtilConstants.OK);
        }

    }
    /********************************************************************/
    /*insert event*/

    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed4", method = RequestMethod.POST)
    @ResponseBody
    JSONObject insertEvent(@RequestBody Event feed, @RequestParam("token_id") long token) throws Exception {
        if (!FeedValidator.validate(feed)) {
            return UtilConstants.getResponseStatus(UtilConstants.INVALID_CONTENT);
        } else {
            JSONArray jsonElements = feed.getFeed_media();
            JsonArray elements = new JsonParser().parse(jsonElements.toString()).getAsJsonArray();
            JSONArray feed_media = new JSONArray();

            for (int i = 0; i < jsonElements.size(); i++) {
                Upload model = uploadRepository.findOne(elements.get(i).getAsJsonObject().get("id").getAsLong());
                if (model != null) {
                    if (model.getMedia_type() == 1) {

                        try {
                            MoveFile.moveFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new Exception("Error with moving photo...", e);
                        }
                        JSONObject media = new JSONObject();

                        media.put("media_name", model.getPath());
                        media.put("media_type", model.getMedia_type());
                        feed_media.add(media);
                        uploadRepository.delete(model.getId());


                    } else {
                        try {
                            MoveFile.moveFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "videos/" + model.getPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new Exception("Error with moving video...", e);
                        }
                        JSONObject media = new JSONObject();
                        media.put("media_name", model.getPath());
                        media.put("media_type", model.getMedia_type());
                        feed_media.add(media);
                        uploadRepository.delete(model.getId());
                    }

                }
            }
            if (feed_media.size() > 0) {
                feed.setFeed_media(feed_media);
            } else feed.setFeed_media(new JSONArray());

            Location location = new Gson().fromJson(feed.getLocation().toJSONString(), Location.class);
            Response response = PlaceIdClient.maps(location.getCoordinates());
            if (response == null) {
                feed.setLocality_id("unavailable");
                feed.setArea_id("unavailable");
            } else {
                String message = null;
                try {
                    message = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Exception("Invalid message for response");
                }
                Places placeInfo = new Gson().fromJson(message, Places.class);

                if (placeInfo.getResults().size() >= 1) {
                    PlaceInfo result_locality = placeInfo.getResults().get(0);
                    PlaceInfo result_area = placeInfo.getResults().get(1);

                    feed.setLocality_id(result_locality.place_id);
                    feed.setArea_id(result_area.place_id);
                } else {
                    feed.setLocality_id("unavailable");
                    feed.setArea_id("unavailable");
                }
            }

            feed = eventRepository.save(feed);
            PushNotification.push(feed, PushNotification.TYPE_FEED_NOTIFICATION);
            PushNotification.pushToUser(feed, tokenRepository, token);
            return UtilConstants.getResponseStatus(UtilConstants.OK);
        }

    }

    @RequestMapping(path = "/setNotify", method = RequestMethod.POST)
    @ResponseBody
    Notify insertNotify(@RequestBody Notify notify) {
        Integer verification;
        verification = notifyRepository.verifyIfExist(notify.getUser_id(), notify.getFeed_id(), notify.getFeed_type());
        if (verification == 0) {
            return notifyRepository.save(notify);
        } else
            return notify;
    }

    @RequestMapping(path = "/getCurrentFeeds", method = RequestMethod.POST)
    @ResponseBody
    FeedsCollection getCurrentFeeds(@RequestParam("locality_id") String locality_id, @RequestParam("user_id") Long user_id) {
        FeedsCollection feedsCollection = new FeedsCollection();
        List<Event> events = eventRepository.getAllEvents(locality_id, TimeUnit.DAYS.toMillis(3));
        for (Event event : events) {
            event.setUsers_interested(voteEventRepository.getInterested(event.getId()));
            event.setPostLikes(voteEventRepository.getJoined(event.getId()));
            event.setVote_status(voteEventRepository.getStatusVote(event.getId(), user_id));
            event.setComments_count(eventsCommentsRepository.numberOfComments(event.getId()));
        }
        if (events.size() > 0) feedsCollection.setEventFeeds(events);

        List<Suggestion> suggestions = suggestionRepository.getAllSuggestion(locality_id);
        for (Suggestion suggestion : suggestions) {
            suggestion.setUsers_supported(voteSuggestionRepository.getSupported(suggestion.getId()));
            suggestion.setUsers_rejected(voteSuggestionRepository.getRejected(suggestion.getId()));
            suggestion.setVote_status(voteSuggestionRepository.getStatusVote(suggestion.getId(), user_id));
            suggestion.setComments_count((suggestionsCommentsRepository.numberOfComments(suggestion.getId())));
        }
        if (suggestions.size() > 0) feedsCollection.setSuggestionFeeds(suggestions);

        List<Trouble> troubles = troubleRepository.getAllTroubles(locality_id);
        for (Trouble trouble : troubles) {
            trouble.setUsers_supported(voteTroubleRepository.getSupported(trouble.getId()));
            trouble.setUsers_rejected(voteTroubleRepository.getRejected(trouble.getId()));
            trouble.setVote_status(voteTroubleRepository.getStatusVote(trouble.getId(), user_id));
            trouble.setComments_count((troublesCommentsRepository.numberOfComments(trouble.getId())));

        }
        if (troubles.size() > 0) feedsCollection.setTroubleFeeds(troubles);

        List<Notify> notifies = notifyRepository.getAllNotifies(user_id);
        if (notifies.size() > 0) feedsCollection.setNotifies(notifies);

        return feedsCollection;
    }


}
