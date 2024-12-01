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

        val movieRecyclerView: RecyclerView = findViewById(R.id.movie_list_recycler_view)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set the initial empty adapter
        movieListAdapter = MovieListAdapter(emptyList(), ::onMovieClick, ::onDeleteClick, ::onWatchedClick)
        movieRecyclerView.adapter = movieListAdapter

        // Observe the LiveData
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()

            // Observe changes in movie list from the database
            movieDao.getAllMovies().observe(this@UserListActivity, { movieList ->
                // Update the adapter with the new list of movies
                movieListAdapter = MovieListAdapter(movieList, ::onMovieClick, ::onDeleteClick, ::onWatchedClick)
                movieRecyclerView.adapter = movieListAdapter
            })
        }

        // Set up navigation buttons
        findViewById<ImageButton>(R.id.homeButton2).setOnClickListener {
            navigateToHome()
        }

        findViewById<ImageButton>(R.id.userListButton2).setOnClickListener {
            navigateToUserList()
        }
    }

    // Handle movie item click (for navigation or other purposes)
    private fun onMovieClick(movie: MovieList) {
        // You can implement navigation or other functionality here
    }

    // Handle delete button click
    private fun onDeleteClick(movie: MovieList) {
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()
            movieDao.deleteMovie(movie) // Assuming you have a deleteMovie() method in DAO
            updateMovieList() // Refresh the movie list
        }
    }

    // Handle watched button click
    private fun onWatchedClick(movie: MovieList) {
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()
            val updatedWatchedStatus = !movie.watched
            movieDao.updateMovieWatchedStatus(movie.id, updatedWatchedStatus)
            updateMovieList() // Refresh the movie list
        }
    }

    // Update the movie list in the adapter
    private fun updateMovieList() {
        lifecycleScope.launch {
            val movieDao = MovieDatabase.getDatabase(this@UserListActivity).MovieListDAO()
            val updatedList = movieDao.getAllMovies().value ?: emptyList()
            movieListAdapter = MovieListAdapter(updatedList, ::onMovieClick, ::onDeleteClick, ::onWatchedClick)
            findViewById<RecyclerView>(R.id.movie_list_recycler_view).adapter = movieListAdapter
        }
    }

    // Navigate to the Home screen
    private fun navigateToHome() {
        val intent = Intent(this, Homepage::class.java)
        startActivity(intent)
    }

    // Navigate to the User List screen
    private fun navigateToUserList() {
        val intent = Intent(this, UserListActivity::class.java)
        startActivity(intent)
    }
}
