package com.movies.nerses.api

import com.movies.nerses.model.Movie
import com.movies.nerses.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/movie/popular")
  suspend fun getMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("/3/movie/{id}")
  suspend fun getDetails(
        @Query("api_key") key: String,
        @Path("id") id: Int
    ): Response<Movie>

}