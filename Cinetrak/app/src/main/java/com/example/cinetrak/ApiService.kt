package com.example.cinetrak

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // Define a function to get the list of popular movies
    @GET("popular")
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    // Define a function to get the list of top-rated movies
    @GET("top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    // Define a function to get the list of upcoming movies
    @GET("upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>
}
