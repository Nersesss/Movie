package com.movies.nerses.repository

import com.movies.nerses.BuildConfig.API_KEY
import com.movies.nerses.api.ApiHelper
import com.movies.nerses.model.Movie

class MoviesRepository(
    private val apiHelper: ApiHelper,
) {

    suspend fun getMovies(
        onResult: (isSuccess: Boolean, message: String, totalPages: Int, movies: MutableList<Movie>) -> Unit,
        page: Int
    ) {
        val filteredMovies = ArrayList<Movie>()

        try {
            val response = apiHelper.getMovies(API_KEY, page)
            if (response.isSuccessful) {
                response.body()?.movies?.forEach(action = {
                    if (!it.adult) filteredMovies.add(it)
                })
            }
            response.body()?.totalPageCount?.let {
                onResult(
                    response.isSuccessful, response.message(),
                    it, filteredMovies
                )
            }

        } catch (e: Exception) {
            e.message?.let { onResult(false, it, 0, filteredMovies) }
        }
    }


    suspend fun getDetails(
        onResult: (isSuccess: Boolean, message: String, movie: Movie?) -> Unit,
        id: Int
    ) {
        try {
            val response = apiHelper.getDetails(API_KEY, id)
            onResult(response.isSuccessful, response.message(), response.body())

        } catch (e: Exception) {
            e.message?.let { onResult(false, it, null) }
        }
    }
}