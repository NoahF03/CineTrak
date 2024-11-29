package com.example.cinetrak

import android.os.Bundle
import android.widget.TextView
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Homepage : AppCompatActivity() {

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieList: List<Movie> // This list should be populated with movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Get the first name from the Intent
        val firstname = intent.getStringExtra("USER_FIRST_NAME") ?: "User"

        val welcomeText: TextView = findViewById(R.id.textView)
        welcomeText.text = "Welcome, $firstname!\uD83D\uDC4B "

        // Initialize the RecyclerView
        movieRecyclerView = findViewById(R.id.movie_recycler_view) // Make sure you have this ID in your XML layout
        movieRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Initialize Retrofit with the base URL and the Gson converter.
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/") // Base URL only
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create the ApiService to define and call API endpoints
        val apiService = retrofit.create(ApiService::class.java)

        // Launch a coroutine to fetch data asynchronously.
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Make the network call asynchronously
                apiService.getMovies(1, "4a5592eeb45e3d65a9e125bdfaea2754")
                    .enqueue(object : Callback<MovieResponse> {
                        override fun onResponse(
                            call: Call<MovieResponse>,
                            response: Response<MovieResponse>
                        ) {
                            // Check if the response is successful
                            if (response.isSuccessful) {
                                val movieResponse = response.body()
                                movieResponse?.let {
                                    // Set up the adapter with the response data
                                    movieAdapter = MovieAdapter(it.results)
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
            } catch (e: Exception) {
                // Handle general exceptions
                e.printStackTrace()
            }
        }
    }
}
