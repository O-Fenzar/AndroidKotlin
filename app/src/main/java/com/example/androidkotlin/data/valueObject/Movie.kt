package com.example.androidkotlin.data.valueObject


import com.google.gson.annotations.SerializedName

data class Movie(
    val page: Int,
    @SerializedName("result")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int

)