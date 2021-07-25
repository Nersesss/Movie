package com.movies.nerses.model

import com.google.gson.annotations.SerializedName

data class Movie(

    val id: Int = 0,
    val adult: Boolean = false,

    @SerializedName("original_title")
    val name: String = "",

    @SerializedName("release_date")
    val date: String = "",

    @SerializedName("overview")
    val description: String = "",
)
