package com.example.dto;

import com.example.entity.feeds.Event;
import com.example.entity.feeds.Suggestion;
import com.example.entity.feeds.Trouble;
import com.example.entity.feeds.Voluntaries;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by freem on 10/26/16.
 */
public class PushNotification {

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

    private static String buildRequest(String feed, String topic, long token){
        String message = "{ \"priority\" : \"high\",\n" +
                "                \"time_to_live\": 30,\n" +
                "                \"collapse_key\":\"geo_zone\",\n" +
                "                \"data\" : {\n" +
                "            \"array\" :\n" + feed +
                "        ,\"token_id\":" + token +
                "        },\n" +
                "            \"to\" : \"/topics/" + topic + "\"" +
                "        }";

        System.out.println(message);
        return message;
    }

    public static void push(Event feed, long token) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildRequest(new Gson().toJson(feed), feed.getLocality_id(), token));
        init(body);
    }

    public static void push(Voluntaries feed, long token) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildRequest(new Gson().toJson(feed), feed.getLocality_id(), token));
        init(body);
    }

    public static void push(Suggestion feed, long token) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildRequest(new Gson().toJson(feed), feed.getLocality_id(), token));
        init(body);
    }

    public static void push(Trouble feed, long token) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, buildRequest(new Gson().toJson(feed), feed.getLocality_id(), token));
        init(body);
    }
}
