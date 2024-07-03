package com.movies.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.models.ErrorModel
import com.movies.models.MovieDetailsResponse
import com.movies.models.ResponseStatus
import com.movies.models.SearchMovieResponse
import com.movies.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieRepo private constructor(){

    private val apiClient = NetworkClient.getNetworkClient()

    companion object{
        @Volatile
        private var instance: MovieRepo? = null

        fun getRepoInstance(): MovieRepo{
            return instance ?: synchronized(this){
                MovieRepo().also {
                    instance = it
                }
            }
        }
    }


    fun searchMovies(query: String):
            LiveData<ResponseStatus<SearchMovieResponse>> {
        val returnData = MutableLiveData<ResponseStatus<SearchMovieResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiClient.searchMovies(query)
                if (response.isSuccessful)
                    returnData.postValue(ResponseStatus.Success(response.body()))
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
        return returnData
    }

    fun getMovieDetails(movieId: String):
            LiveData<ResponseStatus<MovieDetailsResponse>> {
        val returnData = MutableLiveData<ResponseStatus<MovieDetailsResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiClient.getMovieDetails(movieId)
                if (response.isSuccessful)
                    returnData.postValue(ResponseStatus.Success(response.body()))
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
        return returnData
    }




}