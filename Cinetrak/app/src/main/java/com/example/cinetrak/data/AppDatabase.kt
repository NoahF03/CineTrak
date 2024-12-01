package com.example.cinetrak.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotate this class to indicate it is a Room database
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Abstract function to provide access to the DAO
    abstract fun userDao(): UserDAO

    companion object {
        // Volatile annotation ensures that changes to INSTANCE are visible to all threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Function to get the singleton instance of the database
        fun getDatabase(context: Context): AppDatabase {
            // Check if the database instance is already created
            return INSTANCE ?: synchronized(this) {
                // If not, create the database instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Use the application's context to avoid memory leaks
                    AppDatabase::class.java,    // Reference to the database class
                    "cinetrak_database"         // Name of the database file
                ).build()

                // Store the instance to ensure only one instance is created
                INSTANCE = instance
                instance
            }
        }
    }
}
