package com.movies.models


sealed class ResponseStatus<out T>{
    data class Success<T>(val data: T?): ResponseStatus<T>()
    data class Failed(val error: ErrorModel?): ResponseStatus<Nothing>()
}