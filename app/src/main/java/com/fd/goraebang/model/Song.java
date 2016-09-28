package com.fd.goraebang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Song implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("album_id")
    private int albumId;
    @JsonProperty("singer_id")
    private int singerId;
    @JsonProperty("team_id")
    private int teamId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("genre1")
    private String genre1;
    @JsonProperty("genre2")
    private String genre2;
    @JsonProperty("runtime")
    private String runtime;
    @JsonProperty("lyrics")
    private String lyrics;
    @JsonProperty("composer")
    private String composer;
    @JsonProperty("youtube")
    private String youtube;
    @JsonProperty("artist_name")
    private String artistName;
    @JsonProperty("jacket")
    private String jacket;
    @JsonProperty("jacket_middle")
    private String jacketMiddle;
    @JsonProperty("jacket_small")
    private String jacketSmall;
    @JsonProperty("song_tjnum")
    private int songTjnum;
    @JsonProperty("song_num")
    private int songNum;
    @JsonProperty("lowkey")
    private String lowkey;
    @JsonProperty("highkey")
    private String highkey;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("cnt_favorite")
    private int cntFavorite;
    @JsonProperty("is_favorite")
    private boolean isFavorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre1() {
        return genre1;
    }

    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    public String getGenre2() {
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getJacket() {
        return jacket;
    }

    public void setJacket(String jacket) {
        this.jacket = jacket;
    }

    public String getJacketMiddle() {
        return jacketMiddle;
    }

    public void setJacketMiddle(String jacketMiddle) {
        this.jacketMiddle = jacketMiddle;
    }

    public String getJacketSmall() {
        return jacketSmall;
    }

    public void setJacketSmall(String jacketSmall) {
        this.jacketSmall = jacketSmall;
    }

    public int getSongTjnum() {
        return songTjnum;
    }

    public void setSongTjnum(int songTjnum) {
        this.songTjnum = songTjnum;
    }

    public int getSongNum() {
        return songNum;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public String getLowkey() {
        return lowkey;
    }

    public void setLowkey(String lowkey) {
        this.lowkey = lowkey;
    }

    public String getHighkey() {
        return highkey;
    }

    public void setHighkey(String highkey) {
        this.highkey = highkey;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCntFavorite() {
        return cntFavorite;
    }

    public void setCntFavorite(int cntFavorite) {
        this.cntFavorite = cntFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
