package com.example.cinetrak

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide

import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Get the movie data passed from the previous activity
        val moviePoster = intent.getStringExtra("MOVIE_POSTER")
        val movieTitle = intent.getStringExtra("MOVIE_TITLE")
        val movieOverview = intent.getStringExtra("MOVIE_OVERVIEW")
        val movieReleaseDate = intent.getStringExtra("MOVIE_RELEASE_DATE")

        // Initialize views
        val posterImageView: ImageView = findViewById(R.id.movie_detail_poster)
        val titleTextView: TextView = findViewById(R.id.movie_detail_title)
        val overviewTextView: TextView = findViewById(R.id.movie_detail_overview)
        val releaseDateTextView: TextView = findViewById(R.id.movie_detail_release_date)
        val addToListButton: Button = findViewById(R.id.add_to_list_button)

        // Load movie poster using Glide
        val imageUrl = "https://image.tmdb.org/t/p/w500$moviePoster"
        Glide.with(this)
            .load(imageUrl)
            .into(posterImageView)

        // Set movie details
        titleTextView.text = movieTitle
        overviewTextView.text = movieOverview
        releaseDateTextView.text = movieReleaseDate

        // Button click listener to add movie to the database
        addToListButton.setOnClickListener {
            if (movieTitle != null) {
                // Insert movie into database
                lifecycleScope.launch {
                    val movieDatabase = MovieDatabase.getDatabase(this@MovieDetailActivity)
                    val movieListDAO = movieDatabase.MovieListDAO()

                    // Create a MovieList object
                    val movie = MovieList(
                        id = 0,  // Auto-generated id
                        title = movieTitle,
                        watched = false // Default value
                    )

                    // Insert the movie into the database
                    movieListDAO.insertMovie(movie)

                    // Show a message to the user
                    runOnUiThread {
                        showToast("Movie added to your list!")
                    }
                }
            }
        }
    }

    // Utility function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
