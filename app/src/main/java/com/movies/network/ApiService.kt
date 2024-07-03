package com.movies.network

import com.movies.BuildConfig
import com.movies.models.MovieDetailsResponse
import com.movies.models.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("/")
    suspend fun searchMovies(
        @Query("s")query: String?,
        @Query("apikey")apikey: String? = BuildConfig.API_KEY): Response<SearchMovieResponse>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i")id: String?,
        @Query("apikey")apikey: String? = BuildConfig.API_KEY): Response<MovieDetailsResponse>



}