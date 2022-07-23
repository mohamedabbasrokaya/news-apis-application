package com.mabbar.myapplication.api

import com.mabbar.myapplication.NewsResponse

import com.mabbar.myapplication.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservices {
    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey")
        apikey:String
    ):Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apiKey")apikey: String,
        @Query("sources")string: String,


    ):Call<NewsResponse>
}