package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.Banner;
import com.fd.goraebang.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SongService {
    @GET("top100")
    Call<List<Song>> getTopChart(@Query("page") int page);

    @GET("main_banner")
    Call<List<Banner>> getMainBanner();

    @GET("top100")
    Call<List<Song>> getMainNewChart();

    @GET("recom")
    Call<List<Song>> getMyPageAnalysis(@Query("id") String user_id, @Query("page") int page);

    @GET("mySong_read_1")
    Call<List<Song>> getMyPageFavorite(@Query("id") String user_id, @Query("myList_id") String my_list_id, @Query("page") int page);

    @GET("blacklist_song_read")
    Call<List<Song>> getMyPageBlacklist(@Query("id") String id, @Query("page") int page);

    @GET("search_by")
    Call<List<Song>> getSearch(@Query("mytoken") String mytoken, @Query("query") String query, @Query("search_by") String search_by, @Query("page") int page);

    @GET("search_by")
    Call<List<HashMap<String,ArrayList<String>>>> getSearchByAutoComplete(@Query("mytoken") String mytoken, @Query("query") String query, @Query("search_by") String search_by, @Query("page") int page, @Query("auto_complete") boolean auto_complete);

    @GET("search_by")
    Call<List<Song>> getSearchByFilter(@Query("mytoken") String mytoken, @Query("query") String query, @Query("genre") String genre, @Query("age") String age, @Query("gender") String gender, @Query("page") int page);

    @GET("mySong_create")
    Call<Song> insertMyListSong(@Query("id") String id, @Query("myList_id") String myList_id, @Query("song_id") String song_id);

    @GET("mySong_delete")
    Call<Song> deleteMyListSong(@Query("id") String id, @Query("mySong_id") String mySong_id);

    @GET("blacklist_song_create")
    Call<Song> insertBlacklistSong(@Query("id") String id, @Query("song_id") String song_id);

    @GET("blacklist_song_delete")
    Call<Song> deleteBlacklistSong(@Query("id") String id, @Query("blacklist_songs_id") String blacklist_songs_id);
}