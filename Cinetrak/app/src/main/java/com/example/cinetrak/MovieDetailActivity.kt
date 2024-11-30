package com.example.cinetrak

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

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

        // Button click listener (functionality can be added later)
        addToListButton.setOnClickListener {
            // Add to your list functionality can be added here
        }
    }
}
