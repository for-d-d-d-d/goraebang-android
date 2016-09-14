package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AccountService {
    @FormUrlEncoded
    @POST("account/login/")
    Call<User> login(@Field("email") String email, @Field("password") String password);

    @POST("account/refresh/")
    Call<User> refresh(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("account/register/")
    Call<User> register(@Field("email") String email,
                        @Field("nickname") String nickname,
                        @Field("password") String password,
                        @Field("device_type") String device_type,
                        @Field("social_id") String social_id,
                        @Field("social_type") String social_type,
                        @Field("access_token") String access_token
    );

    @GET("account/{user_id}")
    Call<User> getUser(@Header("Authorization") String authorization, @Path("user_id") String user_id);

    @Multipart
    @PUT("account/")
    Call<User> putUser(@Header("Authorization") String Authorization,
                       @Part("image\"; filename=\"user_profile.jpg\" ") RequestBody file,
                       @Part("push_token") String push_token,
                       @Part("name") String nickname,
                       @Part("password") String password,
                       @Part("sex") String sex,
                       @Part("device_type") String device_type,
                       @Part("app_version") String app_version
    );
}