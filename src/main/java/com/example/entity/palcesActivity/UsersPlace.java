package com.example.entity.palcesActivity;


import com.example.entity.user.Profile;

import javax.persistence.*;

@Entity
@Table(name = "cl_user_places")
public class UsersPlace {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Profile user;

    @Column(name = "place_id", nullable = false)
    Long place_id;

    @Column(name = "role_type", nullable = false)
    Integer role_type;

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Long place_id) {
        this.place_id = place_id;
    }

    public Integer getRole_type() {
        return role_type;
    }

    public void setRole_type(Integer role_type) {
        this.role_type = role_type;
    }

    public UsersPlace() {
    }


    public UsersPlace(Profile user_id, Long place_id, Integer role_type) {
        this.user = user_id;
        this.place_id = place_id;
        this.role_type = role_type;
    }
}
