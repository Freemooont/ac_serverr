package com.example.entity.palcesActivity;

import com.example.configs.*;
import com.example.dto.Location;
import com.example.dto.PlaceLocation;
import com.example.entity.feeds.Feed;
import com.example.entity.upload.FeedMedia;
import com.example.entity.upload.Upload;
import com.example.entity.user.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cl_place_items")
public class PlaceItem implements PlaceItemCustom {

    public static final transient int TYPE_PENDING = 1;
    public static final transient int TYPE_APPROVED = 2;
    public static final transient int TYPE_REJECTED = 3;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Convert(converter = FeedMediaConverter.class)
    @Column(name = "avatar", nullable = false)
    private FeedMedia avatar;

    @Convert(converter = LocationArrayConverter.class)
    @Column(name = "locations", nullable = false, length = 1000)
    private List<Location> locations;

    @Convert(converter = PlaceLocationArrayConverter.class)
    @Column(name = "place_locations", nullable = false, length = 1000)
    private List<PlaceLocation> place_locations;

    @Column(name = "create_datetime", nullable = false)
    private long create_datetime;

    @Column(name = "update_datetime", nullable = false)
    private long update_datetime;

    @Column(name = "category", nullable = false)
    private int category;

    private transient int role_type;

    @Column(name = "web_page")
    private String web_page;

    @Convert(converter = JpaArrayConverter.class)
    @Column(name = "phone_numbers")
    private JSONArray phone_numbers;

    @Column(name = "place_status", nullable = false)
    private int place_status;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private Profile owner;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeedMedia getAvatar() {
        return avatar;
    }

    public void setAvatar(FeedMedia avatar) {
        this.avatar = avatar;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public long getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(long create_datetime) {
        this.create_datetime = create_datetime;
    }

    public long getUpdate_datetime() {
        return update_datetime;
    }

    public void setUpdate_datetime(long update_datetime) {
        this.update_datetime = update_datetime;
    }

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getRole_type() {
        return role_type;
    }

    public void setRole_type(int role_type) {
        this.role_type = role_type;
    }

    public String getWeb_page() {
        return web_page;
    }

    public void setWeb_page(String web_page) {
        this.web_page = web_page;
    }

    public JSONArray getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(JSONArray phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    public int getPlace_status() {
        return place_status;
    }

    public void setPlace_status(int place_status) {
        this.place_status = place_status;
    }

    public PlaceItem() {
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<PlaceLocation> getPlace_locations() {
        return place_locations;
    }

    public void setPlace_locations(List<PlaceLocation> place_locations) {
        this.place_locations = place_locations;
    }

    public PlaceItem(String name, String description, FeedMedia avatar, List<Location> locations, long create_datetime, long update_datetime, Profile owner, int category, int role_type, String web_page, JSONArray phone_numbers, int place_status) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.locations = locations;
        this.create_datetime = create_datetime;
        this.update_datetime = update_datetime;
        this.owner = owner;
        this.category = category;
        this.role_type = role_type;
        this.web_page = web_page;
        this.phone_numbers = phone_numbers;
        this.place_status = place_status;
    }


    public boolean checkExist(List<PlaceLocation> placeLocations, PlaceLocation l) {
        return placeLocations.contains(l);
    }
}




