package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "cl_user_tokens")
public class UserTokens {
    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "token",nullable =false)
    String token;

    @Column(name = "user_id",nullable =false)
    Long user_id;

    public UserTokens() {
    }

    public UserTokens(String mobile_token, Long user_id) {
        this.token = mobile_token;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile_token() {
        return token;
    }

    public void setMobile_token(String mobile_token) {
        this.token = mobile_token;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
