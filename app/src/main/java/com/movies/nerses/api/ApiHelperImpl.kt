package com.movies.nerses.api

import com.movies.nerses.model.Movie
import com.movies.nerses.model.Movies
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getMovies(apiKey: String, page: Int): Response<Movies> =
        apiService.getMovies(apiKey, page)

    override suspend fun getDetails(apiKey: String, id: Int): Response<Movie> =
        apiService.getDetails(apiKey, id)

}