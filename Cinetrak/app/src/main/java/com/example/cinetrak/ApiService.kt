package com.example.cinetrak

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //Define a function to get the list of movies, using the Query annotation for query parameters
    @GET("popular")
    fun getMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>
}