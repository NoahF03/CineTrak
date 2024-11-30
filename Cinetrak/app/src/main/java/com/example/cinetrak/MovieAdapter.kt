package com.example.cinetrak


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MovieAdapter(private val movieList: List<Movie>, private val onMovieClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Inflate the layout resource for a single movie item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position] // Get the current movie

        // Construct the full URL for the movie poster image
        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

        // Use Glide to load the image into the ImageView
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imageView)

        // Set up the click listener for the movie poster
        holder.imageView.setOnClickListener {
            onMovieClick(movie) // Pass the clicked movie to the callback
        }
    }

    override fun getItemCount(): Int = movieList.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.movie_poster)
    }
}

