package com.movies.nerses.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.nerses.model.Movie
import com.movies.nerses.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>().apply { value = true }

    var movie = MutableLiveData<Movie>()
    val movies = MutableLiveData<MutableList<Movie>>()
    var currentPage = MutableLiveData(1)
    var totalCount: Int? = null

    private val errorMessageString = MutableLiveData<String>()

    private fun sendErrorMessage(errorString: String) {
        if (errorString.isNotEmpty())
            errorMessageString.value = errorString
    }

    fun getMovies(page: Int) {
        viewModelScope.launch {
            repository.getMovies({ isSuccess, message, totalPages, response ->
                if (isSuccess) {
                    if (response.isNotEmpty()) {
                        totalCount = totalPages
                        movies.value = response
                    } else {
                        sendErrorMessage(message)
                    }
                } else {
                    sendErrorMessage(message)
                }
                isLoading.value = false
            }, page)
        }
    }
    fun getDetails() {
        viewModelScope.launch {
            movie.value?.id?.let {
                repository.getDetails({ isSuccess, message, response ->
                    if (isSuccess) {
                        if (response != null) {
                            movie.value = response
                        } else {
                            sendErrorMessage(message)
                        }
                    } else {
                        sendErrorMessage(message)
                    }
                    isLoading.value = false
                }, it)
            }
        }
    }
}