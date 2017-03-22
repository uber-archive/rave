package com.uber.rave.sample.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uber.rave.sample.RaveActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides dependencies related to making a network request to GitHub.
 */
@Module
public abstract class GitHubModule {

    @RaveActivityScope
    @Provides
    static Retrofit retrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(RaveConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @RaveActivityScope
    @Provides
    static Gson gson() {
        return new GsonBuilder().create();
    }

    @RaveActivityScope
    @Provides
    static GitHubService gitHubService(Retrofit retrofit) {
        return retrofit.create(GitHubService.class);
    }
}
