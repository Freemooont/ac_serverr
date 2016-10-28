package com.example.controllers.feedControllers;

import com.example.dto.Location;
import com.example.dto.PlaceIdClient;
import com.example.dto.PlaceInfo;
import com.example.dto.PushNotification;
import com.example.entity.feeds.*;
import com.example.entity.upload.Upload;
import com.example.repository.feedRepository.EventRepository;
import com.example.repository.feedRepository.SuggestionRepository;
import com.example.repository.feedRepository.TroubleRepository;
import com.example.repository.feedRepository.VoluntariesRepository;
import com.example.repository.upload.UploadRepository;
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
import java.util.List;

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
                          UploadRepository uploadRepository) {
        this.eventRepository = eventRepository;
        this.suggestionRepository = suggestionRepository;
        this.troubleRepository = troubleRepository;
        this.voluntariesRepository = voluntariesRepository;
        this.voteEventRepository = voteEventRepository;
        this.voteSuggestionRepository = voteSuggestionRepository;
        this.voteVoluntaryRepository = voteVoluntaryRepository;
        this.voteTroubleRepository = voteTroubleRepository;
        this.uploadRepository = uploadRepository;
    }


    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @ResponseBody
    Event getEvents(@RequestParam("id") Long id) {
        return eventRepository.findOne(id);
    }


    @RequestMapping(path = "/getFeedByTypee", method = RequestMethod.POST)
    @ResponseBody
    <T extends Feed> List<T> getByType(@RequestParam(value = "feed_type", required = false) int feed_type,
                                       @RequestParam(value = "user_id", required = false) long user_id,
                                       @RequestParam(value = "count", required = false) int count,
                                       @RequestParam(value = "index", required = false) int index) {
        switch (feed_type) {
            case 1:
                return (List<T>) voluntariesRepository.returnByStep(count, index);
            case 2:
                return (List<T>) troubleRepository.returnByStep(count, index);
            case 3:
                return (List<T>) suggestionRepository.returnByStep(count, index);
            case 4:
                return (List<T>) eventRepository.returnByStep(count, index);
            default:
        }
        return null;
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
    List<Event> getEVent(@RequestParam(value = "user_id", required = false) long user_id,
                         @RequestParam(value = "count", required = false) int count,
                         @RequestParam(value = "index", required = false) int index) {
        List<Event> events = eventRepository.returnByStep(count, index);
        for (Event currentEvent : events) {
            currentEvent.setUsers_interested(voteEventRepository.getInterested(currentEvent.getId()));
            currentEvent.setUsers_joined(voteEventRepository.getJoined(currentEvent.getId()));
            currentEvent.setVote_status(voteEventRepository.getStatusVote(currentEvent.getId(), user_id));
        }
        return events;
    }

    /********************************************************************/
    /*get suggestions with step andd offset ( dela - pina la )*/

    /********************************************************************/
    @RequestMapping(path = "/getFeedByType3", method = RequestMethod.POST)
    @ResponseBody
    List<Suggestion> getSugestion(@RequestParam(value = "user_id", required = false) long user_id,
                                  @RequestParam(value = "count", required = false) int count,
                                  @RequestParam(value = "index", required = false) int index) {
        List<Suggestion> suggestions = suggestionRepository.returnByStep(count, index);
        for (Suggestion currentSuggestion : suggestions) {
            currentSuggestion.setUsers_supported(voteSuggestionRepository.getSupported(currentSuggestion.getId()));
            currentSuggestion.setUsers_rejected(voteSuggestionRepository.getRejected(currentSuggestion.getId()));
            currentSuggestion.setVote_status(voteSuggestionRepository.getStatusVote(currentSuggestion.getId(), user_id));
        }
        return suggestions;
    }

    /********************************************************************/
    /*get troubles with step andd offset ( dela - pina la )*/

    /********************************************************************/
    @RequestMapping(path = "/getFeedByType2", method = RequestMethod.POST)
    @ResponseBody
    List<Trouble> getTroubles(@RequestParam(value = "user_id", required = false) long user_id,
                              @RequestParam(value = "count", required = false) int count,
                              @RequestParam(value = "index", required = false) int index) {
        List<Trouble> troubles = troubleRepository.returnByStep(count, index);
        for (Trouble currentTrouble : troubles) {
            currentTrouble.setUsers_supported(voteTroubleRepository.getSupported(currentTrouble.getId()));
            currentTrouble.setUsers_rejected(voteTroubleRepository.getRejected(currentTrouble.getId()));
            currentTrouble.setVote_status(voteTroubleRepository.getStatusVote(currentTrouble.getId(), user_id));
        }
        return troubles;
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
        }
        return voluntaries;
    }

    /********************************************************************/
    /*insert voluntaries*/

    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed1", method = RequestMethod.POST)
    @ResponseBody
    Voluntaries insertVountary(@RequestBody Voluntaries feed) throws IOException {
        JSONArray jsonElements = feed.getFeed_media();
        JsonArray elements = new JsonParser().parse(jsonElements.toString()).getAsJsonArray();
        JSONArray feed_media = new JSONArray();

        for (int i = 0; i < jsonElements.size(); i++) {
            Upload model = uploadRepository.findOne(elements.get(i).getAsJsonObject().get("id").getAsLong());
            if (model != null) {
                if (model.getMedia_type() == 1) {

                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                    JSONObject media = new JSONObject();

                    media.put("media_name", model.getPath());
                    media.put("media_type", model.getMedia_type());
                    feed_media.add(media);
                    uploadRepository.delete(model.getId());


                } else {
                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "videos/" + model.getPath());
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
            String message = response.body().string();
            PlaceInfo placeInfo = new Gson().fromJson(message, PlaceInfo.class);

            if (placeInfo.getResults().size() >= 1) {
                PlaceInfo.Result result_locality = placeInfo.getResults().get(0);
                PlaceInfo.Result result_area = placeInfo.getResults().get(1);

                feed.setLocality_id(result_locality.place_id);
                feed.setArea_id(result_area.place_id);
            } else {
                feed.setLocality_id("unavailable");
                feed.setArea_id("unavailable");
            }
        }

        feed = voluntariesRepository.save(feed);
        PushNotification.push(feed);
        return feed;

    }

    /********************************************************************/
    /*insert trouble*/

    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed2", method = RequestMethod.POST)
    @ResponseBody
    Trouble insertTrouble(@RequestBody Trouble feed) throws IOException {
        JSONArray jsonElements = feed.getFeed_media();
        JsonArray elements = new JsonParser().parse(jsonElements.toString()).getAsJsonArray();
        JSONArray feed_media = new JSONArray();

        for (int i = 0; i < jsonElements.size(); i++) {
            Upload model = uploadRepository.findOne(elements.get(i).getAsJsonObject().get("id").getAsLong());
            if (model != null) {
                if (model.getMedia_type() == 1) {

                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                    JSONObject media = new JSONObject();

                    media.put("media_name", model.getPath());
                    media.put("media_type", model.getMedia_type());
                    feed_media.add(media);
                    uploadRepository.delete(model.getId());


                } else {
                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "videos/" + model.getPath());
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
            String message = response.body().string();
            PlaceInfo placeInfo = new Gson().fromJson(message, PlaceInfo.class);

            if (placeInfo.getResults().size() >= 1) {
                PlaceInfo.Result result_locality = placeInfo.getResults().get(0);
                PlaceInfo.Result result_area = placeInfo.getResults().get(1);

                feed.setLocality_id(result_locality.place_id);
                feed.setArea_id(result_area.place_id);
            } else {
                feed.setLocality_id("unavailable");
                feed.setArea_id("unavailable");
            }
        }

        feed = troubleRepository.save(feed);
        PushNotification.push(feed);
        return feed;

    }

    /********************************************************************/
    /*insert suggestion*/

    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed3", method = RequestMethod.POST)
    @ResponseBody
    Suggestion insertSuggestion(@RequestBody Suggestion feed) throws IOException {
        JSONArray jsonElements = feed.getFeed_media();
        JsonArray elements = new JsonParser().parse(jsonElements.toString()).getAsJsonArray();
        JSONArray feed_media = new JSONArray();

        for (int i = 0; i < jsonElements.size(); i++) {
            Upload model = uploadRepository.findOne(elements.get(i).getAsJsonObject().get("id").getAsLong());
            if (model != null) {
                if (model.getMedia_type() == 1) {

                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                    JSONObject media = new JSONObject();

                    media.put("media_name", model.getPath());
                    media.put("media_type", model.getMedia_type());
                    feed_media.add(media);
                    uploadRepository.delete(model.getId());


                } else {
                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "videos/" + model.getPath());
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
            PlaceInfo placeInfo = new Gson().fromJson(message, PlaceInfo.class);

            if (placeInfo.getResults().size() >= 1) {
                PlaceInfo.Result result_locality = placeInfo.getResults().get(0);
                PlaceInfo.Result result_area = placeInfo.getResults().get(1);

                feed.setLocality_id(result_locality.place_id);
                feed.setArea_id(result_area.place_id);
            } else {
                feed.setLocality_id("unavailable");
                feed.setArea_id("unavailable");
            }
        }

        feed = suggestionRepository.save(feed);
        PushNotification.push(feed);
        return feed;


    }
    /********************************************************************/
    /*insert event*/

    /********************************************************************/
    @RequestMapping(path = "feeds/postFeed4", method = RequestMethod.POST)
    @ResponseBody
    Event insertEvent(@RequestBody Event feed) throws IOException {
        JSONArray jsonElements = feed.getFeed_media();
        JsonArray elements = new JsonParser().parse(jsonElements.toString()).getAsJsonArray();
        JSONArray feed_media = new JSONArray();

        for (int i = 0; i < jsonElements.size(); i++) {
            Upload model = uploadRepository.findOne(elements.get(i).getAsJsonObject().get("id").getAsLong());
            if (model != null) {
                if (model.getMedia_type() == 1) {

                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "photos/" + model.getPath());
                    JSONObject media = new JSONObject();

                    media.put("media_name", model.getPath());
                    media.put("media_type", model.getMedia_type());
                    feed_media.add(media);
                    uploadRepository.delete(model.getId());


                } else {
                    copyFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(), STORAGE_PATH + "videos/" + model.getPath());
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
            String message = response.body().string();
            PlaceInfo placeInfo = new Gson().fromJson(message, PlaceInfo.class);

            if (placeInfo.getResults().size() >= 1) {
                PlaceInfo.Result result_locality = placeInfo.getResults().get(0);
                PlaceInfo.Result result_area = placeInfo.getResults().get(1);

                feed.setLocality_id(result_locality.place_id);
                feed.setArea_id(result_area.place_id);
            } else {
                feed.setLocality_id("unavailable");
                feed.setArea_id("unavailable");
            }
        }

        feed = eventRepository.save(feed);
        PushNotification.push(feed);
        return feed;

    }

    /*private void checkAvailableFolders() {
        File photo = new File("photos/");
        File video = new File("videos/");

        if(!photo.exists())
        {
            photo.mkdir();
        }

        if(!video.exists()){
            video.mkdir();
        }
    }*/

    private static void copyFileUsingStream(String source, String dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            new File(source).delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
        }
    }

}
