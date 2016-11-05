package com.example.controllers.userController;

import com.example.dto.PlaceInfo;
import com.example.entity.user.Settings;
import com.example.repository.userRepository.SettingsRepository;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SettingsController {
    SettingsRepository settingsRepository;

    @Autowired
    public SettingsController(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @RequestMapping(path = "/setCitySetting", method = RequestMethod.POST)
    @ResponseBody
    String insertSettings(@RequestBody PlaceInfo info, @Param("user_id") Long user_id) {

        Settings setting = settingsRepository.getSetting(user_id, info.getPlaceId());
        if (setting == null) {
            setting = new Settings(user_id, info.getPlaceId(), info);
            settingsRepository.save(setting);

            return "{\"status\":1}";
        }
        return "{\"status\":2}";
    }

    @RequestMapping(path = "/getCitySettings", method = RequestMethod.POST)
    @ResponseBody
    String getSettings(@Param("user_id") Long user_id) {
        List<Settings> list = settingsRepository.getListOfSettings(user_id);
        JsonArray jsonArray = new JsonArray();
        Gson gson = new GsonBuilder().create();

        for (Settings info : list) {

            JsonElement json = gson.toJsonTree(info.getPlace_info());
            jsonArray.add(json);
        }

        return jsonArray.toString();
    }

    @RequestMapping(path = "/deleteCitySetting", method = RequestMethod.POST)
    @ResponseBody
    String deleteSettings(@Param("place_id") String place_id, @Param("user_id") Long user_id) {
        int i = settingsRepository.deleteSettings(user_id, place_id);
        return i == 1 ? "{\"status\":1}" : "{\"status\":2}";
    }
}

