package com.movies.db

import androidx.room.Dao
import androidx.room.Query
import com.movies.models.SearchMovieResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE title = :query")
    fun getMovies(query: String): Flow<SearchMovieResponse.MovieItem?>


}