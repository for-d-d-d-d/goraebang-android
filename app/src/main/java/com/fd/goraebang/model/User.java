package com.fd.goraebang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("mylist_id")
    private String myListId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("profile_img_origin")
    private String image;
    @JsonProperty("profile_img_400")
    private String thumbnail;
    @JsonProperty("social_type")
    private String socialType;
    @JsonProperty("social_id")
    private String socialId;
    @JsonProperty("mytoken")
    private String myToken;
    @JsonProperty("result")
    private String result;
    @JsonProperty("mylist_count")
    private int mylistCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyListId() {
        return myListId;
    }

    public void setMyListId(String myListId) {
        this.myListId = myListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getMyToken() {
        return myToken;
    }

    public void setMyToken(String myToken) {
        this.myToken = myToken;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getMylistCount() {
        return mylistCount;
    }

    public void setMylistCount(int mylistCount) {
        this.mylistCount = mylistCount;
    }
}
