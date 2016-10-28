package com.example.dto;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

import static com.example.dto.PlaceInfo.TYPE_ALL;

/**
 * Created by freem on 10/26/16.
 */
public class PlaceIdClient {

    public static Response maps(String location){
        String link = "https://maps.googleapis.com/maps/api/geocode/json?&latlng="+location+"&result_type="+TYPE_ALL+"&key=AIzaSyCl3pWIZXg4X2r-EUUY4h1tCVXhGm58qUE";
        System.out.println(link);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Request request = new Request.Builder()
                .url(link)
                .get()
                .addHeader("content-type", "application/json")
                .build();
        Response response=null;
        try {
            response = client.newCall(request).execute();
            //PlaceInfo placeInfo = new Gson().fromJson(respose,PlaceInfo.class);
            //System.out.println(respose);
            //System.out.println(placeInfo.results.get(0).getFormattedAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
