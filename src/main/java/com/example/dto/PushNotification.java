package com.example.dto;

import com.example.entity.feeds.*;
import com.example.entity.palcesActivity.PlaceItem;
import com.example.entity.palcesActivity.PlaceItemPost;
import com.example.entity.user.UserTokens;
import com.example.repository.userRepository.TokenRepository;
import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class PushNotification {

    public static final int TYPE_FEED_NOTIFICATION = 1;

    public static final int TYPE_USER_NOTIFICATION = 2;

    public static final int TYPE_COMMENT_NOTIFICATION = 3;

    public static final int TYPE_PLACE_ACTIVITY_NOTIFICATION = 4;

    public static final int TYPE_PLACE_ACTIVITY_VALIDATION = 5;

    private static void init(RequestBody body) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "key=AIzaSyBHtENoePdDYisKE91ILSy6fpijEOloZfw")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildFeedNotification(JsonElement feed, String topic, int feed_type, int push_type) {

        JsonObject main = new JsonObject();
        main.addProperty("priority", "high");
        JsonObject data = new JsonObject();
        data.addProperty("push_type", push_type);
        data.addProperty("feed_type", feed_type);
        data.add("feed", feed);

        main.add("data", data);
        main.addProperty("to", "/topics/" + topic);

        String message = main.toString();
        System.out.println(message);
        return message;
    }

    private static String buildUserFeedNotification(JsonElement feed, int feed_type, int push_type, String token) {

        JsonObject main = new JsonObject();
        main.addProperty("priority", "high");
        main.addProperty("time_to_live", 60);
        JsonObject data = new JsonObject();
        data.addProperty("push_type", push_type);
        data.addProperty("feed_type", feed_type);
        data.add("feed", feed);

        main.add("data", data);
        main.addProperty("to", token);

        String message = main.toString();
        System.out.println(message);
        return message;
    }

    public static void push(Event feed, int push_type) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildFeedNotification(gson.toJsonTree(feed), feed.getLocality_id(), Feed.EVENT, push_type));
        init(body);
    }

    public static void push(Voluntaries feed, int push_type) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildFeedNotification(gson.toJsonTree(feed), feed.getLocality_id(), Feed.EVENT, push_type));
        init(body);
    }

    public static void push(Suggestion feed, int push_type) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildFeedNotification(gson.toJsonTree(feed), feed.getLocality_id(), Feed.SUGGESTION, push_type));
        init(body);
    }

    public static void push(Trouble feed, int push_type) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildFeedNotification(gson.toJsonTree(feed), feed.getLocality_id(), Feed.TROUBLE, push_type));
        init(body);
    }

    static JsonSerializer<Timestamp> ser = (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.getTime());

    JsonDeserializer<Timestamp> deser = (json, typeOfT, context) -> json == null ? null : new Timestamp(json.getAsLong());


    public static void pushToUser(Event feed, TokenRepository tokenRepository, long token_id) {
        UserTokens token = tokenRepository.findOne(token_id);
        if (token != null && token.getMobile_token() != null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    buildUserFeedNotification(gson.toJsonTree(feed),
                            Feed.EVENT, TYPE_USER_NOTIFICATION,
                            token.getMobile_token()));
            init(body);
        }
    }

    public static void pushToUser(Trouble feed, TokenRepository tokenRepository, long token_id) {
        UserTokens token = tokenRepository.findOne(token_id);
        if (token != null && token.getMobile_token() != null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    buildUserFeedNotification(gson.toJsonTree(feed),
                            Feed.TROUBLE, TYPE_USER_NOTIFICATION,
                            token.getMobile_token()));
            init(body);
        }
    }

    public static void pushToUser(Suggestion feed, TokenRepository tokenRepository, long token_id) {
        UserTokens token = tokenRepository.findOne(token_id);
        if (token != null && token.getMobile_token() != null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    buildUserFeedNotification(gson.toJsonTree(feed),
                            Feed.SUGGESTION, TYPE_USER_NOTIFICATION,
                            token.getMobile_token()));
            init(body);
        }
    }

    private static String buildUserPlaceValidation(JsonElement place, int feed_type, int push_type, JsonElement tokens) {

        JsonObject main = new JsonObject();
        main.addProperty("priority", "high");
        main.addProperty("time_to_live", 120);
        JsonObject data = new JsonObject();
        data.addProperty("push_type", push_type);
        data.add("place", place);

        main.add("data", data);
        main.add("registration_ids", tokens);

        String message = main.toString();
        System.out.println(message);
        return message;
    }



    public static void notifyUserPlace(PlaceItem item, TokenRepository tokenRepository) {
        List<String> tokens = tokenRepository.getUsersTokens(item.getOwner().getId());
        if (tokens.size() > 0){
            Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    buildUserPlaceValidation(gson.toJsonTree(item),
                            Feed.SUGGESTION, TYPE_PLACE_ACTIVITY_VALIDATION,
                            gson.toJsonTree(tokens)));
            init(body);
        }
    }

    public static void push(PlaceItemPost post, int push_type) {

    }
    public static void pushToUser(PlaceItemPost item, TokenRepository tokenRepository, long token_id) {
        UserTokens token = tokenRepository.findOne(token_id);
        if (token != null && token.getMobile_token() != null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, ser).create();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    buildUserFeedNotification(gson.toJsonTree(item),
                            Feed.SUGGESTION, TYPE_USER_NOTIFICATION,
                            token.getMobile_token()));
            init(body);
        }
    }

}
