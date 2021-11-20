package com.example.corotuinesexample.remote

import com.younis.newapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    @GET("")
    suspend fun getSearchResult(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String,
        @Query("sortBy") sortBy: String
    ): Response<NewsResponse>

}