package com.example.cinetrak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for displaying the list of movies in the movie list.
 * It provides click listeners for viewing movie details, marking movies as watched, and deleting movies from the list.
 */
class MovieListAdapter(
    private val movieList: List<MovieList>,  // List of movies to display
    private val onMovieClick: (MovieList) -> Unit,  // Callback for movie item click
    private val onDeleteClick: (MovieList) -> Unit,  // Callback for delete button click
    private val onWatchedClick: (MovieList) -> Unit  // Callback for watched button click
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    // Creates a new ViewHolder for each movie item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MovieListViewHolder(view)
    }

    // Binds data to each ViewHolder
    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movieList[position] // Get the current movie item

        // Set the movie title and its watched status (either "Watched" or "Not Watched")
        holder.titleTextView.text = movie.title
        holder.watchedTextView.text = if (movie.watched) "Watched" else "Not Watched"

        // Set up click listener for the movie item
        holder.itemView.setOnClickListener {
            onMovieClick(movie)  // Trigger the movie click callback
        }

        // Set up delete button click listener
        holder.deleteButton.setOnClickListener {
            onDeleteClick(movie)  // Trigger the delete click callback
        }

        // Set up watched button click listener
        holder.watchedButton.setOnClickListener {
            onWatchedClick(movie)  // Trigger the watched click callback
        }
    }

    // Returns the total number of items in the movie list
    override fun getItemCount(): Int = movieList.size

    // ViewHolder class to hold the views for each movie item
    class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.movie_title)  // Movie title
        val watchedTextView: TextView = itemView.findViewById(R.id.movie_watched_status)  // Watched status
        val watchedButton: ImageButton = itemView.findViewById(R.id.watched_button)  // Button to mark as watched
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)  // Button to delete the movie from the list
    }
}
