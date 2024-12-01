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

/**
 * Activity that displays detailed information about a selected movie.
 * It shows the movie poster, title, overview, and release date.
 * Allows the user to add the movie to their list.
 */
class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Get the movie data passed from the previous activity (Homepage)
        val moviePoster = intent.getStringExtra("MOVIE_POSTER")
        val movieTitle = intent.getStringExtra("MOVIE_TITLE")
        val movieOverview = intent.getStringExtra("MOVIE_OVERVIEW")
        val movieReleaseDate = intent.getStringExtra("MOVIE_RELEASE_DATE")

        // Initialize the views in the layout
        val posterImageView: ImageView = findViewById(R.id.movie_detail_poster)
        val titleTextView: TextView = findViewById(R.id.movie_detail_title)
        val overviewTextView: TextView = findViewById(R.id.movie_detail_overview)
        val releaseDateTextView: TextView = findViewById(R.id.movie_detail_release_date)
        val addToListButton: Button = findViewById(R.id.add_to_list_button)

        // Load the movie poster image using Glide, which handles image loading
        val imageUrl = "https://image.tmdb.org/t/p/w500$moviePoster"
        Glide.with(this)
            .load(imageUrl) // Load the image from the URL
            .into(posterImageView) // Set the loaded image into the ImageView

        // Set the movie details in the respective TextViews
        titleTextView.text = movieTitle
        overviewTextView.text = movieOverview
        releaseDateTextView.text = movieReleaseDate

        // Set up a click listener for the 'Add to List' button
        addToListButton.setOnClickListener {
            if (movieTitle != null) {
                // Launch a coroutine to insert the movie into the database asynchronously
                lifecycleScope.launch {
                    val movieDatabase = MovieDatabase.getDatabase(this@MovieDetailActivity)  // Get the database instance
                    val movieListDAO = movieDatabase.MovieListDAO()  // Get the DAO (Data Access Object)

                    // Create a MovieList object to insert into the database
                    val movie = MovieList(
                        id = 0,  // Auto-generate the id when inserting the movie
                        title = movieTitle,
                        watched = false // Default watched status is false
                    )

                    // Insert the movie into the MovieList table in the database
                    movieListDAO.insertMovie(movie)

                    // Show a success message to the user after insertion
                    runOnUiThread {
                        showToast("Movie added to your list!") // Show a toast notification
                    }
                }
            }
        }
    }

    // Helper function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
