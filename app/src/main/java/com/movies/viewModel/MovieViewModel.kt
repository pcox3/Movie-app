package com.movies.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.Repository.MovieRepo
import com.movies.models.MovieDetailsResponse
import com.movies.models.ResponseStatus
import com.movies.models.SearchMovieResponse

class MovieViewModel(application: Application): AndroidViewModel(application) {

    private val repo = MovieRepo.getRepoInstance()

    fun searchMovies(query: String): LiveData<ResponseStatus<SearchMovieResponse>> {
        return repo.searchMovies( query)
    }

    fun getMovieDetails(movieId: String): LiveData<ResponseStatus<MovieDetailsResponse>> {
        return repo.getMovieDetails( movieId)
    }

}