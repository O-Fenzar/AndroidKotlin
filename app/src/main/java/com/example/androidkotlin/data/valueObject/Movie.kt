package com.example.androidkotlin.data.valueObject


import com.google.gson.annotations.SerializedName

data class Movie(

    val id: Int,
    val title: String,
    @SerializedName("released_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath:String
)


