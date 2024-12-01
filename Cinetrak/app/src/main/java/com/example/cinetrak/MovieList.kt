package com.example.cinetrak

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list")
data class MovieList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val watched: Boolean = false
)
