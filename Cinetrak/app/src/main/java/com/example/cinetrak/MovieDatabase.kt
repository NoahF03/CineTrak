package com.example.cinetrak

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database class for the Movie database.
 * This class is used to manage the database, provide access to DAOs, and handle database operations.
 */
@Database(entities = [MovieList::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    // Abstract function to get the DAO (Data Access Object) for the MovieList entity.
    abstract fun MovieListDAO(): MovieListDAO

    companion object {
        // Volatile ensures that the instance of the database is immediately visible to all threads.
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        // Singleton pattern to get the single instance of the database.
        fun getDatabase(context: Context): MovieDatabase {
            // If the instance is not null, return it. Otherwise, create a new instance.
            return INSTANCE ?: synchronized(this) {
                // Build the database instance using Room database builder
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, // Class of the database
                    "Movie_database" // Name of the database file
                ).build()

                // Assign the instance to the INSTANCE variable
                INSTANCE = instance
                instance
            }
        }
    }
}
