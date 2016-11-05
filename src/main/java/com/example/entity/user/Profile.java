package com.example.entity.user;

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

    @Column(name = "avatar",nullable =false)
    String avatar;

    transient String mobile_token;

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

    public Profile(String first_name, String last_name, String avatar) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }



    public Profile(Long id, String first_name, String last_name, Long fb_id, JSONObject settings, String avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.fb_id = fb_id;

        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", fb_id=" + fb_id +
                ", avatar='" + avatar + '\'' +
                ", mobile_token='" + mobile_token + '\'' +
                '}';
    }
}
