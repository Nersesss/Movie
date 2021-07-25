package com.movies.nerses.api

import com.movies.nerses.model.Movie
import com.movies.nerses.model.Movies
import retrofit2.Response

interface ApiHelper {

    suspend fun getMovies(apiKey: String, page: Int): Response<Movies>
    suspend fun getDetails(apiKey: String, id: Int): Response<Movie>
}