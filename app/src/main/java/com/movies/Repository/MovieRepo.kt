package com.movies.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.db.MovieDao
import com.movies.db.MovieDb.Companion.getDatabase
import com.movies.models.ErrorModel
import com.movies.models.MovieDetailsResponse
import com.movies.models.ResponseStatus
import com.movies.models.SearchMovieResponse
import com.movies.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieRepo internal constructor(application: Application){

    private val apiClient = NetworkClient.getNetworkClient()
    private val doa: MovieDao

    fun searchMovies(query: String):
            LiveData<ResponseStatus<SearchMovieResponse>> {
        val returnData = MutableLiveData<ResponseStatus<SearchMovieResponse>>()
        CoroutineScope(Dispatchers.IO).launch {

            /**
             * Check if movie is available in local DB
             */
            doa.getMovies(query).let {
                if (it.isEmpty()) {
                    try {
                        val response = apiClient.searchMovies(query)
                        if (response.isSuccessful){
                            doa.insertMovies(response.body()?.search?: emptyList())
                            returnData.postValue(ResponseStatus.Success(response.body()))
                        }
                        else
                            returnData.postValue(
                                ResponseStatus.Failed(
                                    ErrorModel(response.message())
                                )
                            )
                    } catch (ex: Exception) {
                        ex.localizedMessage
                        returnData.postValue(ResponseStatus.Failed(ErrorModel(ex.localizedMessage)))
                    }
                }else{
                    returnData.postValue(ResponseStatus
                        .Success(SearchMovieResponse(search = it, page = 0, response = true)))
                }
            }
        }
        return returnData
    }

    fun getMovieDetails(movieId: String):
            LiveData<ResponseStatus<MovieDetailsResponse>> {
        val returnData = MutableLiveData<ResponseStatus<MovieDetailsResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            doa.getMoviesDetails(movieId).let {
                if (it != null) {
                    returnData.postValue(ResponseStatus.Success(it))
                }else{
                    try {
                        val response = apiClient.getMovieDetails(movieId)
                        if (response.isSuccessful){
                            doa.insertMoviesDetails(response.body()!!)
                            returnData.postValue(ResponseStatus.Success(response.body()))
                        }
                        else
                            returnData.postValue(
                                ResponseStatus.Failed(
                                    ErrorModel(response.message())
                                )
                            )
                    } catch (ex: Exception) {
                        ex.localizedMessage
                        returnData.postValue(ResponseStatus.Failed(ErrorModel(ex.localizedMessage)))
                    }
                }
            }
        }
        return returnData
    }


    init {
        val db = getDatabase(application)
        doa = db!!.daoClass()
    }


}