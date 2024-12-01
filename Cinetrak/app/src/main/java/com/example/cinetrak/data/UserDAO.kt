package com.example.cinetrak.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// The @Dao annotation marks this interface as a Data Access Object (DAO).
// DAOs are responsible for handling the database operations such as insert, update, and query.
@Dao
interface UserDAO {

    // The @Insert annotation is used for inserting a new User into the database.
    // The onConflict = OnConflictStrategy.ABORT means that if there's a conflict (e.g., a duplicate entry),
    // the insert operation will be aborted and no changes will be made.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    // This @Query annotation defines a query that selects a User from the database based on the provided username and password.
    // The query is designed to return one User record (LIMIT 1) that matches the provided username and password.
    // If no matching user is found, the function will return null.
    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): User?

    // This @Query annotation defines a query that checks whether a username already exists in the database.
    // It returns the count of rows that match the provided username. If the username exists, the count will be greater than 0.
    @Query("SELECT COUNT(*) FROM user_table WHERE username = :username")
    suspend fun doesUsernameExist(username: String): Int
}
