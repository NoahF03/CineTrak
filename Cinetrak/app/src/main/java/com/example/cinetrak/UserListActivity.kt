package com.example.cinetrak

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class UserListActivity : ComponentActivity() {

    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // Initialize the RecyclerView and set its layout manager to LinearLayoutManager
        val movieRecyclerView: RecyclerView = findViewById(R.id.movie_list_recycler_view)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set an initial empty adapter for the RecyclerView
        movieListAdapter = MovieListAdapter(emptyList(), ::onMovieClick, ::onDeleteClick, ::onWatchedClick)
        movieRecyclerView.adapter = movieListAdapter

        // Use lifecycleScope to launch a coroutine to observe LiveData
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()

            // Observe the movie list from the database and update the adapter when data changes
            movieDao.getAllMovies().observe(this@UserListActivity, { movieList ->
                // Update the adapter with the new movie list
                movieListAdapter = MovieListAdapter(movieList, ::onMovieClick, ::onDeleteClick, ::onWatchedClick)
                movieRecyclerView.adapter = movieListAdapter
            })
        }

        // Set up navigation buttons with their click listeners
        findViewById<ImageButton>(R.id.homeButton2).setOnClickListener {
            navigateToHome() // Navigate to the home screen
        }

        findViewById<ImageButton>(R.id.userListButton2).setOnClickListener {
            navigateToUserList() // Navigate to the user list screen
        }
    }

    // Handle the movie item click event (could be for navigation or additional actions)
    private fun onMovieClick(movie: MovieList) {
        // Implement any specific functionality when a movie is clicked
    }

    // Handle the delete button click event
    private fun onDeleteClick(movie: MovieList) {
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()
            movieDao.deleteMovie(movie) // Delete the selected movie from the database
            updateMovieList() // Refresh the movie list after deletion
        }
    }

    // Handle the watched button click event to toggle the watched status
    private fun onWatchedClick(movie: MovieList) {
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()
            val updatedWatchedStatus = !movie.watched // Toggle the watched status
            movieDao.updateMovieWatchedStatus(movie.id, updatedWatchedStatus) // Update the database
            updateMovieList() // Refresh the movie list after the status update
        }
    }

    // Refresh the movie list and update the RecyclerView adapter with the new data
    private fun updateMovieList() {
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()
            val updatedList = movieDao.getAllMovies().value ?: emptyList() // Fetch the updated list of movies
            movieListAdapter = MovieListAdapter(updatedList, ::onMovieClick, ::onDeleteClick, ::onWatchedClick)
            findViewById<RecyclerView>(R.id.movie_list_recycler_view).adapter = movieListAdapter // Update the RecyclerView adapter
        }
    }

    // Navigate to the Home screen
    private fun navigateToHome() {
        val intent = Intent(this, Homepage::class.java) // Create an Intent to start the Homepage activity
        startActivity(intent) // Start the Homepage activity
    }

    // Navigate to the User List screen (current activity)
    private fun navigateToUserList() {
        val intent = Intent(this, UserListActivity::class.java) // Create an Intent to start the UserListActivity
        startActivity(intent) // Start the UserListActivity (current activity)
    }
}
