package com.example.repository.placesActivityRepository;


import com.example.entity.palcesActivity.PlaceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.repository.placesActivityRepository.PlaceItemRepository.GET_LIST_BY_USER_ID;
import static com.example.repository.placesActivityRepository.PlaceItemRepository.GET_PENDINGS;

public interface PlaceItemRepository extends JpaRepository<PlaceItem,Long>{
    //TODO get by user id
    //TODO get pending by user id who is admin

    String GET_LIST_BY_USER_ID = "SELECT * FROM cl_place_items WHERE id  in (:places_ids) ";
    String GET_PENDINGS = "SELECT * FROM cl_place_items WHERE place_status = 1";

    @Query(value = GET_LIST_BY_USER_ID,nativeQuery = true)
    List<PlaceItem> gtPlaceItems(@Param("places_ids") Object[] area_id);

    @Query(value = GET_PENDINGS , nativeQuery = true)
    List<PlaceItem> getPendingsList();
}
