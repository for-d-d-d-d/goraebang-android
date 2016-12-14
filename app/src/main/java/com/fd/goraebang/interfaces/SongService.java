package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.Banner;
import com.fd.goraebang.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;


import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;

import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;



public interface SongService {
// Update for [ project ], refactoring on [ REST API ] with [ restful url ]

// >>> Interface API DO
    //  Public ~> MainBanner
    //  GET     /api/main_banner
    //  to:     "api/interface#main_banner"
    //  params  null
    @GET("main_banner")
    Call<List<Banner>> getMainBanner();

    //  Public ~> Top100
    //  GET     /api/top100
    //  to:     "api/interface#top100"
    //  params  'mytoken', 'page', ('column')
    @GET("top100")
    Call<List<Song>> getTopChart(
            @Query("mytoken") String mytoken,
            @Query("page") int page);

    //  Public ~> NewSong for This Month
    //  GET     /api/month_new
    //  to:     "api/interface#month_new"
    //  params  'mytoken', 'page'
    @GET("month_new")
    Call<List<Song>> getMainNewChart(
            @Query("mytoken") String mytoken,
            @Query("page") int page);

    //  Public ~> Search by Conditional Filter
    //  GET     /api/filter_by
    //  to:     "api/interface#filter_by"
    //  params  'mytoken', 'page', ('query', 'search_by'), ('genre', 'age', 'gender')
    @GET("filter_by") // search by conditional filter
    Call<List<Song>> getSearchByFilter(
            @Query("mytoken") String mytoken,
            @Query("query") String query,
            @Query("search_by") String search_by,
            @Query("genre") String genre,
            @Query("age") String age,
            @Query("gender") String gender,
            @Query("page") int page);

    //  Public ~> Search by AutoComplete
    //  GET     /api/search_by
    //  to:     "api/interface#search_by"
    //  params  'mytoken', 'page', 'auto_complete', ('query', 'search_by')
    @GET("search_by") // search on auto complete
    Call<List<HashMap<String,ArrayList<String>>>> getSearchByAutoComplete(
            @Query("mytoken") String mytoken,
            @Query("query") String query,
            @Query("search_by") String search_by,
            @Query("page") int page,
            @Query("auto_complete") boolean auto_complete);

    //  Public ~> Search in General
    //  GET     /api/search_by
    //  to:     "api/interface#filter_by"
    //  params  'mytoken', 'page', 'query', 'search_by', ('genre', 'age', 'gender')
    @GET("search_by") // search on common
    Call<List<Song>> getSearch(
            @Query("mytoken") String mytoken,
            @Query("query") String query,
            @Query("search_by") String search_by,
            @Query("genre") String genre,
            @Query("age") String age,
            @Query("gender") String gender,
            @Query("page") int page);

    //  Public ~> Recommandation for My Taste
    //  GET     /api/recom
    //  to:     "api/interface#recom"
    //  params  'id'(user id), 'page'
    @GET("recom")
    Call<List<Song>> getMyPageAnalysis(
            @Query("id") String user_id,
            @Query("page") int page);
// <<< END

// >>> MySong API DO
    //  MylistSong ~> CREAT
    //  POST    /api/mylist/:mylist_id/my_song
    //  to:     "api/my_song#create"
    //  params  '<url> mylist_id', 'user_id', 'song_id'
    @FormUrlEncoded
    @POST("mylist/{mylist_id}/my_song")
    Call<Song> createMyListSong(
            @Path("mylist_id") String mylist_id,
            @Field("user_id") String user_id,
            @Field("song_id") int song_id);

    //  MylistSong ~> READ
    //  GET     /api/mylist/:mylist_id/my_song
    //  to:     "api/my_song#index"
    //  params  '<url> mylist_id', 'user_id', 'page'
    @GET("mylist/{mylist_id}/my_song")
    Call<List<Song>> getMyPageFavorite(
            @Path("mylist_id") String my_list_id,
            @Query("user_id") String user_id,
            @Query("page") int page);

    //  MylistSong ~> DELETE
    //  DELETE  /api/mylist/:mylist_id/my_song/:id
    //  to:     "api/my_song#destroy"
    //  params  '<url> mylist_id', '<url> id', 'user_id', ('song_id')
    @DELETE("mylist/{mylist_id}/my_song/{id}")
    Call<List<Song>> deleteMyListSong(
            @Path("mylist_id") String mylist_id,
            @Path("id") String mysong_id,
            @Query("user_id") String user_id,
            @Query("song_id") int song_id);
// <<< END

// >>> BlacklistSong API DO
    //  BlacklistSong ~> CREAT
    //  POST    /api/blacklist_song
    //  to:     "api/blacklist_song#create"
    //  params  'user_id', 'song_id'
    @FormUrlEncoded
    @POST("blacklist_song")
    Call<Song> createBlacklistSong(
            @Field("user_id") String user_id,
            @Field("song_id") int song_id);

    //  BlacklistSong ~> READ
    //  GET     /api/blacklist_song
    //  to:     "api/blacklist_song#index"
    //  params  'user_id', 'page'
    @GET("blacklist_song")
    Call<List<Song>> getMyPageBlacklist(
            @Query("user_id") String user_id,
            @Query("page") int page);

    //  BlacklistSong ~> DELETE
    //  DELETE  /api/blacklist_song/:id
    //  to:     "api/blacklist_song#destroy"
    //  params  '<url> id', 'user_id'
    @DELETE("blacklist_song/{id}")
    Call<List<Song>> deleteBlacklistSong(
            @Path("id") int song_id,
            @Query("user_id") String user_id);
// <<< END

}