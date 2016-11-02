package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountService {
    @POST("login/")
    Call<User> login(
            @Query("user[email]") String email,
            @Query("user[password]") String password
    );

    @POST("regist/")
    Call<User> register(@Query("user[email]") String email,
                        @Query("user[name]") String name,
                        @Query("user[gender]") String gender,
                        @Query("user[password]") String password,
                        @Query("user[password_confirmation]") String password_confirmation
    );

    @GET("my_account/")
    Call<User> me(@Query("mytoken") String token);


    //@Multipart
    @GET("user_modify/")
    Call<User> modify(@Query("mytoken") String mytoken,
                        @Query("user[name]") String name,
                        @Query("user[gender]") String gender,
                        @Query("current_password") String current_password,
                        @Query("new_password") String new_password,
                        @Query("new_password_confirm") String new_password_confirm,
                        @Query("upload_img") String img
                        //@Query("image\"; filename=\"user_profile.jpg\" ") RequestBody file
    );
}