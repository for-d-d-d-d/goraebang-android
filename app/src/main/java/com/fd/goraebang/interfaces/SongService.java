package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.Banner;
import com.fd.goraebang.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SongService {
    @GET("top100")
    Call<List<Song>> getTopChart(@Query("page") int page);

    @GET("banner")
    Call<List<Banner>> getMainBanner();

    @GET("top100")
    Call<List<Song>> getMainNewChart();

    @GET("song")
    Call<List<Song>> getMyPageAnalysis(@Query("id") String user_id, @Query("page") int page);

    @GET("mySong_read")
    Call<List<Song>> getMyPageFavorite(@Query("id") String user_id, @Query("myList_id") String my_list_id, @Query("page") int page);

    @GET("blacklist_song_read")
    Call<List<Song>> getMyPageBlacklist(@Query("id") String id, @Query("page") int page);

    @GET("search")
    Call<List<Song>> getSearch(@Query("[query]") String query, @Query("page") int page);

    @GET("search_by_title")
    Call<List<Song>> getSearchByTitle(@Query("[query]") String query, @Query("page") int page);

    @GET("search_by_artist")
    Call<List<Song>> getSearchByArtist(@Query("[query]") String query, @Query("page") int page);

    @GET("search_by_lyrics")
    Call<List<Song>> getSearchByLyrics(@Query("[query]") String query, @Query("page") int page);
}