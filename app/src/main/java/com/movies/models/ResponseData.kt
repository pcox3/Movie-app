package com.movies.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    @SerializedName("Search") val search:MutableList<MovieItem>,
    @SerializedName("totalResults") val page:Long,
    @SerializedName("Response") val response:Boolean,
)

@Entity(tableName = "movies")
data class MovieItem(
    @SerializedName("Title") val title:String?,
    @SerializedName("Year") val year:String?,
    @PrimaryKey
    @NonNull
    @SerializedName("imdbID") val imdbID:String,
    @SerializedName("Type") val type:String?,
    @SerializedName("Poster") val poster:String?,
)

data class ErrorModel(
    @SerializedName("Error") val error:String?
)

@Entity(tableName = "movies_details")
data class MovieDetailsResponse(
    @SerializedName("Actors") val actors: String?,
    @SerializedName("Awards") val awards: String?,
    @SerializedName("BoxOffice") val boxOffice: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("DVD") val dVD: String?,
    @SerializedName("Director") val director: String?,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Language") val language: String?,
    @SerializedName("Metascore") val metascore: String?,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("Production") val production: String?,
    @SerializedName("Rated") val rated: String?,
    //@SerializedName("Ratings") val ratings: List<Rating>,
    @SerializedName("Released") val released: String?,
    @SerializedName("Response") val response: String?,
    @SerializedName("Runtime") val runtime: String?,
    @SerializedName("Title") val title: String?,
    @SerializedName("Type") val type: String?,
    @SerializedName("Website") val website: String?,
    @SerializedName("Writer") val writer: String?,
    @SerializedName("Year") val year: String?,
    @PrimaryKey
    @NonNull
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("imdbVotes") val imdbVotes: String?
)
data class Rating(
    @SerializedName("Source") val source: String?,
    @SerializedName("Value") val value: String?
)