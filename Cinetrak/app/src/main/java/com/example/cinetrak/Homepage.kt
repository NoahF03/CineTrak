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

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieList: List<Movie> // This list should be populated with movies

    private val apiKey = "4a5592eeb45e3d65a9e125bdfaea2754" // Use your actual API key
    private var currentEndpoint: String = "popular" // Default endpoint to 'popular'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Get the first name from the Intent
        val firstname = intent.getStringExtra("USER_FIRST_NAME") ?: "User"

        val welcomeText: TextView = findViewById(R.id.textView)
        welcomeText.text = "Welcome, $firstname!\uD83D\uDC4B "

        // Initialize the RecyclerView
        movieRecyclerView = findViewById(R.id.movie_recycler_view)
        movieRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Initialize Retrofit with the base URL and the Gson converter.
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/") // Base URL only
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create the ApiService to define and call API endpoints
        val apiService = retrofit.create(ApiService::class.java)

        // Button click listeners to change the endpoint
        findViewById<Button>(R.id.TopRated).setOnClickListener {
            currentEndpoint = "top_rated"
            fetchMovies(apiService)
            resetButtonBackgrounds()
            it.setBackgroundResource(R.drawable.rounded_dark_grey)  // Set the pressed color background
        }

        findViewById<Button>(R.id.PopularButton).setOnClickListener {
            currentEndpoint = "popular"
            fetchMovies(apiService)
            resetButtonBackgrounds()
            it.setBackgroundResource(R.drawable.rounded_dark_grey)  // Set the pressed color background
        }

        findViewById<Button>(R.id.UpcomingButton).setOnClickListener {
            currentEndpoint = "upcoming"
            fetchMovies(apiService)
            resetButtonBackgrounds()
            it.setBackgroundResource(R.drawable.rounded_dark_grey)  // Set the pressed color background
        }

        // Fetch movies initially
        fetchMovies(apiService)

        // Set up the buttons in the corners
        findViewById<ImageButton>(R.id.logoutButton).setOnClickListener {
            // Handle logout action
            logout()
        }

        findViewById<ImageButton>(R.id.homeButton).setOnClickListener {
            // Navigate to Home
            navigateToHome()
        }

        findViewById<ImageButton>(R.id.userListButton).setOnClickListener {
            // Navigate to User List
            navigateToUserList()
        }
    }

    // Helper function to reset other buttons' backgrounds
    private fun resetButtonBackgrounds() {
        findViewById<Button>(R.id.TopRated).setBackgroundResource(R.drawable.roundedwhite)
        findViewById<Button>(R.id.PopularButton).setBackgroundResource(R.drawable.roundedwhite)
        findViewById<Button>(R.id.UpcomingButton).setBackgroundResource(R.drawable.roundedwhite)
    }

    // Function to fetch movies based on the current endpoint
    private fun fetchMovies(apiService: ApiService) {
        val call: Call<MovieResponse> = when (currentEndpoint) {
            "top_rated" -> apiService.getTopRatedMovies(1, apiKey)
            "upcoming" -> apiService.getUpcomingMovies(1, apiKey)
            else -> apiService.getPopularMovies(1, apiKey)
        }

        // Make the network call asynchronously
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                // Check if the response is successful
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    movieResponse?.let {
                        // Set up the adapter with the response data
                        movieAdapter = MovieAdapter(it.results) { movie ->
                            // Navigate to the MovieDetailActivity when a movie is clicked
                            val intent = Intent(this@Homepage, MovieDetailActivity::class.java).apply {
                                putExtra("MOVIE_POSTER", movie.poster_path)
                                putExtra("MOVIE_TITLE", movie.title)
                                putExtra("MOVIE_OVERVIEW", movie.overview)
                                putExtra("MOVIE_RELEASE_DATE", movie.release_date)
                            }
                            startActivity(intent)
                        }
                        movieRecyclerView.adapter = movieAdapter
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
        // You can add your logout logic here, e.g., clearing session or redirecting to a login screen.
        // Example:
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Navigate to the Home screen
    private fun navigateToHome() {
        // You can navigate to a home screen or perform any action
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
