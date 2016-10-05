package com.example.entity;

import com.example.configs.JpaConverterJson;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "cl_user")
public class Profile {



    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "first_name",nullable =false)
    String first_name;

    @Column(name = "last_name",nullable =false)
    String last_name;

    @Column(name = "fb_id",nullable =false)
    Long fb_id;

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "settings",nullable =false)
    JSONObject settings;

    @Column(name = "avatar",nullable =false)
    String avatar;

    @Column(name = "mobile_token",nullable =false)
    String mobile_token;

    /*@OneToMany
    private Event events;

    @OneToMany(mappedBy = "profile_id",cascade = CascadeType.ALL)
    public Event getEvents() {
        return events;
    }

    public void setEvents(Event events) {
        this.events = events;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Long getFb_id() {
        return fb_id;
    }

    public void setFb_id(Long fb_id) {
        this.fb_id = fb_id;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile_token() {
        return mobile_token;
    }

    public void setMobile_token(String mobile_token) {
        this.mobile_token = mobile_token;
    }

    public Profile() {
    }

    public JSONObject getSettings() {
        return settings;
    }

    public void setSettings(JSONObject settings) {
        this.settings = settings;
    }

    public Profile(String first_name, String last_name, Long fb_id, JSONObject settings, String avatar, String mobile_token) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.fb_id = fb_id;
        this.settings = settings;
        this.avatar = avatar;
        this.mobile_token = mobile_token;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", fb_id=" + fb_id +
                ", settings='" + settings + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile_token='" + mobile_token + '\'' +
                '}';
    }
}
