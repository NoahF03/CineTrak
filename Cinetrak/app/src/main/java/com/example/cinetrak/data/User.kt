package com.example.cinetrak.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// This annotation marks the class as a Room Entity, which means it represents a table in the database.
@Entity(tableName = "user_table")
data class User(

    // The @PrimaryKey annotation marks this field as the primary key for the table.
    // Setting autoGenerate = true means Room will automatically generate a unique value for the 'id' field when inserting a new row.
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    // The 'firstname' field will be a column in the 'user_table'.
    val firstname: String,

    // The 'username' field will also be a column in the 'user_table'.
    val username: String,

    // The 'password' field will also be a column in the 'user_table'.
    val password: String
)
