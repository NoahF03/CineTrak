package com.example.cinetrak

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Activity representing the homepage of the Cinetrak app.
 * Displays a welcome message and a horizontally scrolling list of movies fetched from an API.
 */
class Homepage : AppCompatActivity() {

    private lateinit var movieRecyclerView: RecyclerView // RecyclerView for displaying the list of movies
    private lateinit var movieAdapter: MovieAdapter // Adapter for the movie list
    private lateinit var movieList: List<Movie> // List of movies to be populated from API

    private val apiKey = "4a5592eeb45e3d65a9e125bdfaea2754" // API key for accessing the movie API
    private var currentEndpoint: String = "popular" // Default endpoint to 'popular'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Retrieve the first name of the user from the Intent extras
        val firstname = intent.getStringExtra("USER_FIRST_NAME") ?: "User"

        val welcomeText: TextView = findViewById(R.id.textView)
        welcomeText.text = "Welcome, $firstname!\uD83D\uDC4B" // Display a welcome message

        // Initialize the RecyclerView to display the list of movies
        movieRecyclerView = findViewById(R.id.movie_recycler_view)
        movieRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) // Horizontal layout

        // Initialize Retrofit with the base URL and the Gson converter for parsing JSON responses
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/") // API base URL
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson to convert JSON to Kotlin objects
            .build()

        // Create the ApiService to define API endpoints
        val apiService = retrofit.create(ApiService::class.java)

        // Set up button click listeners to change the endpoint and fetch different movie lists
        findViewById<Button>(R.id.TopRated).setOnClickListener {
            currentEndpoint = "top_rated" // Change the endpoint to 'top_rated'
            fetchMovies(apiService) // Fetch movies for the selected endpoint
            resetButtonBackgrounds() // Reset button background colors
            it.setBackgroundResource(R.drawable.rounded_dark_grey) // Set the pressed color background
        }

        findViewById<Button>(R.id.PopularButton).setOnClickListener {
            currentEndpoint = "popular" // Change the endpoint to 'popular'
            fetchMovies(apiService) // Fetch movies for the selected endpoint
            resetButtonBackgrounds() // Reset button background colors
            it.setBackgroundResource(R.drawable.rounded_dark_grey) // Set the pressed color background
        }

        findViewById<Button>(R.id.UpcomingButton).setOnClickListener {
            currentEndpoint = "upcoming" // Change the endpoint to 'upcoming'
            fetchMovies(apiService) // Fetch movies for the selected endpoint
            resetButtonBackgrounds() // Reset button background colors
            it.setBackgroundResource(R.drawable.rounded_dark_grey) // Set the pressed color background
        }

        // Fetch movies initially when the activity is created
        fetchMovies(apiService)

        // Set up the buttons in the corners for logout and navigation
        findViewById<ImageButton>(R.id.logoutButton).setOnClickListener {
            logout() // Handle logout action
        }

        findViewById<ImageButton>(R.id.homeButton).setOnClickListener {
            navigateToHome() // Navigate to the home screen
        }

        findViewById<ImageButton>(R.id.userListButton).setOnClickListener {
            navigateToUserList() // Navigate to the user list screen
        }
    }

    // Helper function to reset other buttons' backgrounds to the default color
    private fun resetButtonBackgrounds() {
        findViewById<Button>(R.id.TopRated).setBackgroundResource(R.drawable.roundedwhite)
        findViewById<Button>(R.id.PopularButton).setBackgroundResource(R.drawable.roundedwhite)
        findViewById<Button>(R.id.UpcomingButton).setBackgroundResource(R.drawable.roundedwhite)
    }

    // Function to fetch movies from the API based on the selected endpoint
    private fun fetchMovies(apiService: ApiService) {
        // Determine the appropriate API call based on the current endpoint
        val call: Call<MovieResponse> = when (currentEndpoint) {
            "top_rated" -> apiService.getTopRatedMovies(1, apiKey) // Fetch top rated movies
            "upcoming" -> apiService.getUpcomingMovies(1, apiKey) // Fetch upcoming movies
            else -> apiService.getPopularMovies(1, apiKey) // Fetch popular movies (default)
        }

        // Make the network call asynchronously using Retrofit
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                // Check if the response is successful
                if (response.isSuccessful) {
                    val movieResponse = response.body() // Get the response body
                    movieResponse?.let {
                        // Set up the adapter with the fetched movie list
                        movieAdapter = MovieAdapter(it.results) { movie ->
                            // Navigate to the MovieDetailActivity when a movie is clicked
                            val intent = Intent(this@Homepage, MovieDetailActivity::class.java).apply {
                                putExtra("MOVIE_POSTER", movie.poster_path)
                                putExtra("MOVIE_TITLE", movie.title)
                                putExtra("MOVIE_OVERVIEW", movie.overview)
                                putExtra("MOVIE_RELEASE_DATE", movie.release_date)
                            }
                            startActivity(intent) // Start the movie details activity
                        }
                        movieRecyclerView.adapter = movieAdapter // Set the adapter to the RecyclerView
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("Homepage", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle failure
                Log.e("Homepage", "Network request failed", t)
            }
        })
    }

    // Handle logout logic
    private fun logout() {
        // You can add your logout logic here, e.g., clearing session or redirecting to a login screen
        val intent = Intent(this, MainActivity::class.java) // Navigate to the login screen
        startActivity(intent)
        finish() // Close the current activity
    }

    // Navigate to the Home screen
    private fun navigateToHome() {
        // Navigate to the home screen (this could be used to refresh or go to the homepage)
        val intent = Intent(this, Homepage::class.java)
        startActivity(intent)
    }

    // Navigate to the User List screen
    private fun navigateToUserList() {
        // Navigate to the user list screen
        val intent = Intent(this, UserListActivity::class.java)
        startActivity(intent)
    }
}
