package com.uber.rave.sample.github;

import com.uber.rave.sample.github.model.Repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/repos/{owner}/{repo}")
    Call<Repo> listRepos(@Path("owner") String owner, @Path("repo") String repo);
}
