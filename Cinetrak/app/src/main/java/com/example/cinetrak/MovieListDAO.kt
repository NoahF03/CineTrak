package com.example.cinetrak

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieListDAO {

    /**
     * Inserts a movie into the movie list.
     * If a movie with the same ID already exists, it will be replaced.
     * @param movie The movie to insert.
     * @return The ID of the inserted movie (auto-generated if the movie's ID is 0).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieList): Long

    /**
     * Retrieves all movies from the movie list.
     * The result is wrapped in a LiveData to observe data changes.
     * @return A LiveData list of movies.
     */
    @Query("SELECT * FROM movie_list")
    fun getAllMovies(): LiveData<List<MovieList>>

    /**
     * Updates the 'watched' status of a movie based on its ID.
     * @param movieId The ID of the movie to update.
     * @param watched The new 'watched' status (true or false).
     */
    @Query("UPDATE movie_list SET watched = :watched WHERE id = :movieId")
    suspend fun updateMovieWatchedStatus(movieId: Int, watched: Boolean)

    /**
     * Deletes a movie from the movie list.
     * @param movie The movie to delete.
     */
    @Delete
    suspend fun deleteMovie(movie: MovieList) // Delete the movie from the database
}
