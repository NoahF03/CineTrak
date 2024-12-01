package com.example.cinetrak

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieListDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieList): Long

    @Query("SELECT * FROM movie_list")
    fun getAllMovies(): LiveData<List<MovieList>>

    @Query("UPDATE movie_list SET watched = :watched WHERE id = :movieId")
    suspend fun updateMovieWatchedStatus(movieId: Int, watched: Boolean)

    @Delete
    suspend fun deleteMovie(movie: MovieList) // Delete the movie from the database

}
