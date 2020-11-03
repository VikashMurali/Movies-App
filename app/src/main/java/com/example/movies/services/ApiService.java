package com.example.movies.services;

import com.example.movies.model.HeroClass;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Observable<List<HeroClass>> getMoviesList();

}
