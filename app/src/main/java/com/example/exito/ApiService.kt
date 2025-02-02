package com.example.exito

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("people/mekBot/books/want-to-read.json")
    suspend fun getBook(): Response<ReadingListResponse>

    @GET("search.json")
    suspend fun searchBook(
        @Query("q") query: String,
        @Query("page") page: Int? = 1
    ): Response<SearchResponse>

}