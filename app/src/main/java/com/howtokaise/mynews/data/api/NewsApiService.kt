package com.howtokaise.mynews.data.api

import com.howtokaise.mynews.data.remote.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService{
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("category") category : String,
        @Query("apiKey") apiKey : String
    ): NewsResponse
}


