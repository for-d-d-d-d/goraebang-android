package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SongService {
    @GET("songs/")
    Call<List<Song>> getProducts(@Query("page") int page);
}