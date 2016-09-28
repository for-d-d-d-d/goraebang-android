package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AccountService {
    @GET("login/")
    Call<User> login(
            @Query("user[email]") String email,
            @Query("user[password]") String password
    );

    @GET("regist/")
    Call<User> register(@Query("user[email]") String email,
                        @Query("user[name]") String name,
                        @Query("user[gender]") String gender,
                        @Query("user[password]") String password,
                        @Query("user[password_confirmation]") String password_confirmation
    );
}