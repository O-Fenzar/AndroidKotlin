package com.example.androidkotlin.data.api

import com.example.androidkotlin.data.valueObject.MovieDetails
import com.example.androidkotlin.data.valueObject.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=38f4762494383020e9deeecb782ac319&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=38f4762494383020e9deeecb782ac319
    // https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

}