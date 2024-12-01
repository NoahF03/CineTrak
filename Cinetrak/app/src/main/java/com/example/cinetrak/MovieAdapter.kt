package com.example.cinetrak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Adapter for displaying a list of movies in a RecyclerView.
 * Binds movie data to the view and handles click events.
 */
class MovieAdapter(private val movieList: List<Movie>, private val onMovieClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // Called when a new ViewHolder is needed (recycled or created)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Inflate the layout resource for a single movie item (item_movie.xml)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view) // Return a new ViewHolder with the inflated view
    }

    // Binds data to the ViewHolder at the given position
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position] // Get the movie data at the current position

        // Construct the full URL for the movie poster image
        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

        // Use Glide to load the image into the ImageView in the ViewHolder
        Glide.with(holder.itemView.context)
            .load(imageUrl) // URL of the movie poster image
            .into(holder.imageView) // Target ImageView to load the image into

        // Set up a click listener on the ImageView to handle movie poster clicks
        holder.imageView.setOnClickListener {
            onMovieClick(movie) // Trigger the callback, passing the clicked movie
        }
    }

    // Return the total number of items in the movie list
    override fun getItemCount(): Int = movieList.size

    // ViewHolder for a single movie item
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.movie_poster) // ImageView to display the movie poster
    }
}
