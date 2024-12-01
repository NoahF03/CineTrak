package com.example.cinetrak

data class MovieResponse(
    val page: Int,               // The current page number of the response
    val results: List<Movie>,    // A list of Movie objects that represents the movie data
    val total_pages: Int,        // The total number of pages available
    val total_results: Int       // The total number of results available across all pages
)
