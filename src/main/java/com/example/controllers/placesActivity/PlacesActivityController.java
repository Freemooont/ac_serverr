package com.example.controllers.placesActivity;

import com.example.UtilConstants;
import com.example.configs.MoveFile;
import com.example.controllers.feedControllers.FeedValidator;
import com.example.dto.*;
import com.example.entity.feeds.Event;
import com.example.entity.palcesActivity.PlaceItem;
import com.example.entity.palcesActivity.PlaceItemPost;
import com.example.entity.palcesActivity.UsersPlace;
import com.example.entity.upload.FeedMedia;
import com.example.entity.upload.Upload;
import com.example.entity.user.Profile;
import com.example.repository.commentsRepository.PlaceItemPostCommentsRepository;
import com.example.repository.placesActivityRepository.PlaceItemPostRepository;
import com.example.repository.placesActivityRepository.PlaceItemRepository;
import com.example.repository.placesActivityRepository.UsersPlaceRepository;
import com.example.repository.upload.UploadRepository;
import com.example.repository.userRepository.SettingsRepository;
import com.example.repository.userRepository.TokenRepository;
import com.example.repository.votesRepository.VotePlacePostRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PlacesActivityController {
    PlaceItemRepository placeItemRepository;
    UsersPlaceRepository usersPlaceRepository;
    private UploadRepository uploadRepository;
    private TokenRepository tokenRepository;
    private PlaceItemPostRepository placeItemPostRepository;
    private SettingsRepository settingsRepository;
    private VotePlacePostRepository votePlacePostRepository;
    private PlaceItemPostCommentsRepository placeItemPostCommentsRepository;


    public static String STORAGE_PATH = "/opt/tomcat/storage/";

    @Autowired
    public PlacesActivityController(PlaceItemRepository placeItemRepository, UsersPlaceRepository usersPlaceRepository, UploadRepository uploadRepository, TokenRepository tokenRepository, PlaceItemPostRepository placeItemPostRepository, SettingsRepository settingsRepository, VotePlacePostRepository votePlacePostRepository, PlaceItemPostCommentsRepository placeItemPostCommentsRepository) {
        this.placeItemRepository = placeItemRepository;
        this.usersPlaceRepository = usersPlaceRepository;
        this.uploadRepository = uploadRepository;

        this.tokenRepository = tokenRepository;
        this.placeItemPostRepository = placeItemPostRepository;
        this.settingsRepository = settingsRepository;
        this.votePlacePostRepository = votePlacePostRepository;
        this.placeItemPostCommentsRepository = placeItemPostCommentsRepository;
    }

    @RequestMapping(path = "/insertPlaceItem", method = RequestMethod.POST)
    @ResponseBody
    JSONObject savePlaceItem(@RequestBody PlaceItem placeItem) throws IOException {
        if (!PlaceItemValidator.validate(placeItem)) {
            return UtilConstants.getResponseStatus(UtilConstants.INVALID_CONTENT);
        } else {
            FeedMedia avatar = placeItem.getAvatar();
            if (avatar.getId() > 0) {
                Upload model = uploadRepository.findOne(avatar.getId());
                if (model != null) {
                    if (model.getMedia_type() == 1) {
                        MoveFile.moveFileUsingStream(STORAGE_PATH + "temp_media/" + model.getPath(),
                                STORAGE_PATH + "photos/" + model.getPath());
                        FeedMedia media_final = new FeedMedia(model.getPath(), model.getMedia_type());
                        placeItem.setAvatar(media_final);
                        uploadRepository.delete(model.getId());
                    }
                }
            }

            List<Location> locations = placeItem.getLocations();
            List<PlaceLocation> placeLocations = new ArrayList<>();
            for (Location location : locations) {
                Response response = PlaceIdClient.maps(location.getCoordinates());
                if (response != null) {
                    String message = null;
                    message = response.body().string();
                    Places placeInfo = new Gson().fromJson(message, Places.class);

                    if (placeInfo.getResults().size() >= 1) {
                        PlaceInfo result_locality = placeInfo.getResults().get(0);
                        PlaceInfo result_area = placeInfo.getResults().get(1);
                        PlaceLocation l = new PlaceLocation(result_area.place_id, result_locality.getPlaceId());
                        if(placeItem.checkExist(placeLocations, l)){
                            placeLocations.add(l);
                        }
                    }
                }
            }

            placeItem.setPlace_locations(placeLocations);
            placeItem = placeItemRepository.save(placeItem);
            UsersPlace usersPlace = new UsersPlace(placeItem.getOwner(), placeItem.getId(), Profile.ROLE_PLACE_OWNER);
            usersPlaceRepository.save(usersPlace);
            return UtilConstants.getResponseStatus(UtilConstants.OK);
        }
    }

    @RequestMapping(path = "/changePlaceStatus", method = RequestMethod.POST)
    @ResponseBody
    JSONObject savePlaceItem(@RequestParam("place_item_id") Long id, @RequestParam("status") int status) throws IOException {
        PlaceItem item = placeItemRepository.findOne(id);
        if (item != null) {
            item.setPlace_status(status);
            placeItemRepository.save(item);
            PushNotification.notifyUserPlace(item, tokenRepository);
            return UtilConstants.getResponseStatus(UtilConstants.OK);
        } else {
            return UtilConstants.getResponseStatus(UtilConstants.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/getPlacesListByUser", method = RequestMethod.POST)
    @ResponseBody
    List<PlaceItem> getPlacesList(@RequestParam("user_id") Long user_id) {
        List<Long> userPlaces = usersPlaceRepository.getPLacesByUser(user_id);
        if (userPlaces.size() > 0) {
            List<PlaceItem> placeItems = placeItemRepository.gtPlaceItems(userPlaces.toArray());
            if (placeItems.size() > 0) {
                for (PlaceItem p : placeItems) {
                    UsersPlace user = usersPlaceRepository.getPlaceUser(user_id, p.getId());
                    p.setRole_type(user.getRole_type());
                }
                return placeItems;
            } else
                return new ArrayList<>();
        } else
            return new ArrayList<>();
    }

    //TODO veerify if user is admin with user_id
    @RequestMapping(path = "/getPendingPlaces", method = RequestMethod.POST)
    @ResponseBody
    List<PlaceItem> getPendingPlaces(@RequestParam("user_id") Long user_id) {
        return placeItemRepository.getPendingsList();
    }

    @RequestMapping(path = "/insertUserPlace", method = RequestMethod.POST)
    @ResponseBody
    JSONObject insertUserPlace(@RequestBody List<UsersPlace> usersPlace, @RequestParam("user_id") Long user_id) {
        if (usersPlace != null) {
            usersPlaceRepository.save(usersPlace);

            return UtilConstants.getResponseStatus(UtilConstants.OK);
        } else {
            return UtilConstants.getResponseStatus(UtilConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/deleteUserPlace", method = RequestMethod.POST)
    @ResponseBody
    JSONObject deleteUserPlace(@RequestBody UsersPlace usersPlace, @RequestParam("user_id") Long user_id) {
        usersPlaceRepository.delete(usersPlace);
        return UtilConstants.getResponseStatus(UtilConstants.OK);
    }

    @RequestMapping(path = "/getPlaceUsers", method = RequestMethod.POST)
    @ResponseBody
    List<UsersPlace> getPLacesUsers(@RequestParam("place_id") Long place_id) {
        return usersPlaceRepository.getPlaceUser(place_id);
    }

    @RequestMapping(path = "/getPlacesPostList", method = RequestMethod.POST)
    @ResponseBody
    List<PlaceItemPost> getEvent(@RequestParam(value = "user_id", required = false) long user_id,
                                 @RequestParam(value = "count", required = false) int count,
                                 @RequestParam(value = "index", required = false) int index) {
        List<String> place_ids = settingsRepository.getPlacesIds(user_id);
        if (place_ids != null && place_ids.size() > 0) {

            List<PlaceItemPost> places = placeItemPostRepository.returnByStep(count, index, place_ids.toArray());
            for (PlaceItemPost placeItem : places) {
                placeItem.setPostLikes(votePlacePostRepository.getLikes(placeItem.getId()));
                placeItem.setVote_status(votePlacePostRepository.getStatusVote(placeItem.getId(), user_id));
                placeItem.setComments_count(placeItemPostCommentsRepository.numberOfComments(placeItem.getId()));
            }
            return places;
        } else
            return new ArrayList<>();

    }

    @RequestMapping(path = "postPlacesPost", method = RequestMethod.POST)
    @ResponseBody
    JSONObject insertPlaceItemPost(@RequestBody PlaceItemPost post, @RequestParam("token_id") long token) throws Exception {
        if (!PlaceItemValidator.validate(post)) {
            return UtilConstants.getResponseStatus(UtilConstants.INVALID_CONTENT);
        } else {
            JSONArray jsonElements = post.getFeed_media();
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
                post.setFeed_media(feed_media);
            } else post.setFeed_media(new JSONArray());


            post = placeItemPostRepository.save(post);
            PushNotification.push(post, PushNotification.TYPE_PLACE_ACTIVITY_NOTIFICATION);
            PushNotification.pushToUser(post, tokenRepository, token);
            return UtilConstants.getResponseStatus(UtilConstants.OK);
        }

    }

}
