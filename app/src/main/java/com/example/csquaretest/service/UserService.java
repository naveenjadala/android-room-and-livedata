package com.example.csquaretest.service;

import com.example.csquaretest.model.Respons;
import com.example.csquaretest.model.UserModel;
import com.example.csquaretest.model.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("login")
    Call<Respons> loginUser(@Body UserModel userModel);

    @GET("users")
    Call<Users> getUsers(@Query("page") int page);
}
