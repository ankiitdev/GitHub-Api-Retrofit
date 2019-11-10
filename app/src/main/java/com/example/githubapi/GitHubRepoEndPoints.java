package com.example.githubapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubRepoEndPoints {

    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("user") String name);
}
