package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {
    @GET("post/it")
    Call<List<Post>> getPosts();
}