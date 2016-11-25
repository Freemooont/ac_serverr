package com.example.entity.upload;

public class FeedMedia {


    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_VIDEO = 2;

    Long id;

    String media_url;

    Integer media_type;

    public FeedMedia() {
    }

    public FeedMedia(String media_url, Integer media_type) {
        this.id = id;
        this.media_url = media_url;
        this.media_type = media_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public Integer getMedia_type() {
        return media_type;
    }

    public void setMedia_type(Integer media_type) {
        this.media_type = media_type;
    }
}
