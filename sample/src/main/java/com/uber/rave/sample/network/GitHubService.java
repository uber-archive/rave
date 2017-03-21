package com.uber.rave.sample.network;

import com.uber.rave.sample.network.model.Repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit API for making HTTP calls to GitHub.
 */
public interface GitHubService {
    @GET("/repos/{owner}/{repo}")
    Call<Repo> listRepos(@Path("owner") String owner, @Path("repo") String repo);
}
