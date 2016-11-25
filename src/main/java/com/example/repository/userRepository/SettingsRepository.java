package com.example.repository.userRepository;

import com.example.configs.JpaConverterJson;
import com.example.dto.PlaceInfo;
import com.example.entity.user.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Convert;
import javax.persistence.Converter;
import java.util.List;

import static com.example.repository.userRepository.SettingsRepository.CHECK_EXIS_USER_WITH_PLACE;
import static com.example.repository.userRepository.SettingsRepository.GET_PLACE_IDs_BY_USER;

/**
 * Created by mike on 11/3/16.
 */
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    String CHECK_EXIS_USER_WITH_PLACE = "select * from cl_user_settings s where s.user_id = :user_id AND s.place_id = :place_id";
    String DELETE_BY_USER_AND_PLACE = "DELETE from cl_user_settings s where s.user_id = :user_id AND s.place_id = :place_id";
    String GET_LIST_OF_USERS = "select * from cl_user_settings s where s.user_id = :user_id";
    String GET_PLACE_IDs_BY_USER = "SELECT place_id as area_id FROM cl_user_settings WHERE user_id = :user_id ";

    @Query(value = CHECK_EXIS_USER_WITH_PLACE, nativeQuery = true)
    Settings getSetting(@Param("user_id") Long user_id, @Param("place_id") String place_id);

    @Query(value = GET_LIST_OF_USERS, nativeQuery = true)
    List<Settings> getListOfSettings(@Param("user_id") Long user_id);

    @Transactional
    @Modifying
    @Query(value = DELETE_BY_USER_AND_PLACE, nativeQuery = true)
    int deleteSettings(@Param("user_id") Long user_id, @Param("place_id") String place_id);

    @Query(value = GET_PLACE_IDs_BY_USER,nativeQuery = true)
    List<String> getPlacesIds(@Param("user_id") Long user_id);
}
