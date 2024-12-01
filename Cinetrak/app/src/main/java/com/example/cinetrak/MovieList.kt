package com.example.cinetrak

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a movie in the movie list database table.
 * The class is annotated with @Entity to indicate that it corresponds to a table in Room's database.
 */
@Entity(tableName = "movie_list")
data class MovieList(
    // The @PrimaryKey annotation designates this property as the primary key for the table.
    // autoGenerate = true means that Room will automatically generate a unique ID for each movie.
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    // The title of the movie.
    val title: String,

    // The 'watched' status of the movie (defaults to false if not provided).
    // This field tracks whether the user has watched the movie or not.
    val watched: Boolean = false
)
