package com.example.entity.user;

import com.example.configs.JpaConverterPlaceInfo;
import com.example.dto.PlaceInfo;
import com.example.dto.Places;

import javax.persistence.*;

/**
 * Created by mike on 11/3/16.
 */
@Entity
@Table(name = "cl_user_settings")
public class Settings {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "user_id",nullable =false)
    Long user_id;

    @Column(name = "place_id",nullable =false)
    String place_id;

    @Convert(converter = JpaConverterPlaceInfo.class)
    @Column(name = "place_info",nullable =false)
    PlaceInfo place_info;

    public Settings() {

    }

    public Settings(Long user_id, String place_id, PlaceInfo place_info) {
        this.user_id = user_id;
        this.place_id = place_id;
        this.place_info = place_info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public PlaceInfo getPlace_info() {
        return place_info;
    }

    public void setPlace_info(PlaceInfo place_info) {
        this.place_info = place_info;
    }


}
