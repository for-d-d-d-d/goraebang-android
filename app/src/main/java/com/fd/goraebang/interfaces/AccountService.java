package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountService {

    // => (로그인) POST        /api/user/login                       api/user#login
    // STORY   > 수동 로그인 및 토큰 발행
    // params  > 'user[email]', 'user[password]', or ('user[mytoken]')
    @FormUrlEncoded
    @POST("user/login")
    Call<User> login(
            @Field("user[email]") String email,
            @Field("user[password]") String password);

    // => (신규 생성) POST     /api/user                             api/user#create
    // STORY   > 회원가입
    // params  > 'user[email]', 'user[name]', 'user[gender]', 'user[password]', 'user[password_confirmation]'
    @FormUrlEncoded
    @POST("user")
    Call<User> register(
            @Field("user[email]") String email,
            @Field("user[name]") String name,
            @Field("user[gender]") String gender,
            @Field("user[password]") String password,
            @Field("user[password_confirmation]") String password_confirmation);

    // => (정보 조회) GET      /api/user/:id                         api/user#show
    // STORY   > 다목적성 자기계정 조회
    // params  > 'id'(mytoken)
    @GET("user/{id}")
    Call<User> me(
            @Path("id") String mytoken);

    // => (정보 수정) PUT      /api/user/:id(.:format)               api/user#update
    // STORY   > 계정 수정정보 저장
    // params  > '<url> id'(mytoken), 'user[name]', 'user[gender]', [ ('current_password'), ('new_password'), ('new_password_confirm') ]
    //@Multipart
    @PUT("user/{id}")
    Call<User> modify(
            @Path("id") String mytoken,
            @Query("user[name]") String name,
            @Query("user[gender]") String gender,
            @Query("current_password") String current_password,
            @Query("new_password") String new_password,
            @Query("new_password_confirm") String new_password_confirm,
            //@Query("image\"; filename=\"user_profile.jpg\" ") RequestBody file,
            @Query("upload_img") String img);
}