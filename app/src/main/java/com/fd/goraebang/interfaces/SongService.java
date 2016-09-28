package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SongService {
    @GET("top100/")
    Call<List<Song>> getTopChart(@Query("page") int page);
}