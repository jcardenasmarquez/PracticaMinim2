package com.jcardenas.example.practicaminim2;

import com.jcardenas.example.practicaminim2.models.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubAPI {

    @GET("/users/{username}")
    Call<User> infoUser (@Path("username") String user);

    @GET("/users/{username}/followers")
    Call<List<User>> listaFollowers(@Path("username") String user);
}
