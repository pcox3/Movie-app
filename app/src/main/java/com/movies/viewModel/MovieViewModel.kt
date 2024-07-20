package com.movies.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.Repository.MovieRepo
import com.movies.models.MovieDetailsResponse
import com.movies.models.MovieItem
import com.movies.models.ResponseStatus
import com.movies.models.SearchMovieResponse

class MovieViewModel(application: Application): AndroidViewModel(application) {

    private val repo = MovieRepo(application)

    var title = ObservableField<String>()
    var movieDirector = ObservableField<String>()
    var movieOverview = ObservableField<String>()
    var movieReleaseDate = ObservableField<String>()
    var movieGenre = ObservableField<String>()


    fun setMovieDetails(movie: MovieDetailsResponse?) {
        title.set("Title: ${movie?.title}")
        movieDirector.set("Director: ${movie?.director}")
        movieOverview.set("Description: ${movie?.plot}")
        movieReleaseDate.set("Release Date: ${movie?.year}")
        movieGenre.set("Genre: ${movie?.genre}")
    }

    fun searchMovies(query: String): LiveData<ResponseStatus<SearchMovieResponse>> {
        return repo.searchMovies( query)
    }

    fun getMovieDetails(movieId: String): LiveData<ResponseStatus<MovieDetailsResponse>> {
        return repo.getMovieDetails( movieId)
    }

}