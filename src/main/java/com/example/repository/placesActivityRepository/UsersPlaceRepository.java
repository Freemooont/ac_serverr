package com.example.repository.placesActivityRepository;

import com.example.entity.palcesActivity.UsersPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.repository.placesActivityRepository.UsersPlaceRepository.GET_PLACES_BY_USER;
import static com.example.repository.placesActivityRepository.UsersPlaceRepository.GET_USERS_PLACE;

public interface UsersPlaceRepository extends JpaRepository<UsersPlace, Long> {
    String GET_PLACES_BY_USER = "SELECT t.place_id FROM cl_user_places t WHERE t.user_id = :user_id";
    String GET_USERS_PLACE = "SELECT * FROM cl_user_places u WHERE u.user_id=:user_id AND u.place_id=:place_id";
    String GET_USER_PLACES_BY_PLACE_ID = "SELECT * FROM cl_user_places u WHERE u.place_id=:place_id";


    @Query(value = GET_PLACES_BY_USER, nativeQuery = true)
    List<Long> getPLacesByUser(@Param("user_id") Long user_id);

    @Query(value = GET_USERS_PLACE,nativeQuery = true)
    UsersPlace getPlaceUser(@Param("user_id") Long user_id, @Param("place_id") Long place_id);

    @Query(value = GET_USER_PLACES_BY_PLACE_ID,nativeQuery = true)
    List<UsersPlace> getPlaceUser(@Param("place_id") Long place_id);
}
