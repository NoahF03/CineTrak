package com.example.cinetrak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieListAdapter(
    private val movieList: List<MovieList>,
    private val onMovieClick: (MovieList) -> Unit,
    private val onDeleteClick: (MovieList) -> Unit,
    private val onWatchedClick: (MovieList) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        // Inflate the layout for each movie item in the list
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MovieListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movieList[position] // Get the current movie item

        // Set up the movie title and watched status
        holder.titleTextView.text = movie.title
        holder.watchedTextView.text = if (movie.watched) "Watched" else "Not Watched"

        // Set up the click listener for each item
        holder.itemView.setOnClickListener {
            onMovieClick(movie) // Pass the clicked movie to the callback
        }

        // Set up the delete button click listener
        holder.deleteButton.setOnClickListener {
            onDeleteClick(movie) // Pass the movie to the delete callback
        }

        // Set up the watched button click listener
        holder.watchedButton.setOnClickListener {
            onWatchedClick(movie) // Pass the movie to the watched callback
        }
    }

    override fun getItemCount(): Int = movieList.size

    // ViewHolder class to hold the views for each item
    class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.movie_title)
        val watchedTextView: TextView = itemView.findViewById(R.id.movie_watched_status)
        val watchedButton: ImageButton = itemView.findViewById(R.id.watched_button)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }
}