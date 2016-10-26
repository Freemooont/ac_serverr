package com.example.entity.upload;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cl_temp_media")
public class Upload {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable =false)
    Long id;

    @Column(name = "path_media",nullable =false)
    String path_media;

    @Column(name = "date_time",nullable = false)
    Timestamp timestamp;

    @Column(name = "media_type",nullable = false)
    Integer media_type;

    public Upload() {
    }

    public Upload(String path, Timestamp timestamp, Integer media_type) {
        this.path_media = path;
        this.timestamp = timestamp;
        this.media_type = media_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path_media;
    }

    public void setPath(String path) {
        this.path_media = path;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMedia_type() {
        return media_type;
    }

    public void setMedia_type(Integer media_type) {
        this.media_type = media_type;
    }
}
