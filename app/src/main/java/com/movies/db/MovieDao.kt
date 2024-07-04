package com.movies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.models.MovieDetailsResponse
import com.movies.models.MovieItem


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%'")
    fun getMovies(query: String): MutableList<MovieItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesDetails(movies: MovieDetailsResponse)

    @Query("SELECT * FROM movies_details WHERE imdbID = :id")
    fun getMoviesDetails(id: String): MovieDetailsResponse?

}