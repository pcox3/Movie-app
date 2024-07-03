package com.movies.network

import com.movies.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkClient {

    private val client: OkHttpClient.Builder = OkHttpClient.Builder()
        .followRedirects(true)
        .retryOnConnectionFailure(true)
        .connectTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)

    fun getNetworkClient(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

}