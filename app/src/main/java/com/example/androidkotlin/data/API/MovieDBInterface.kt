package com.example.androidkotlin.data.API

import com.example.androidkotlin.data.valueObject.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=38f4762494383020e9deeecb782ac319
    // https://api.themoviedb.org/3/movie/299534?api_key=38f4762494383020e9deeecb782ac319
    // https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>


}