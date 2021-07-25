package com.movies.nerses.model

import com.google.gson.annotations.SerializedName

data class Movies(

    val page: Int,

    @SerializedName("results")
    val movies: List<Movie>,

    @SerializedName("total_pages")
    val totalPageCount: Int,

    @SerializedName("total_results")
    val totalResults: String,
)
