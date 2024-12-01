package com.example.cinetrak

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.cinetrak.data.AppDatabase
import com.example.cinetrak.data.User
import kotlinx.coroutines.launch

class Register : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize EditText fields for user input
        val firstnameField: EditText = findViewById(R.id.firstname_create_input)
        val usernameField: EditText = findViewById(R.id.username_create_input)
        val passwordField: EditText = findViewById(R.id.password_create_input)
        val passwordConfirmField: EditText = findViewById(R.id.password_confirm_input)

        // Initialize the button that triggers user creation
        val createButton: Button = findViewById(R.id.create_btn)

        // Set an OnClickListener for the button
        createButton.setOnClickListener {
            // Get the text entered by the user in the fields
            val firstname = firstnameField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val passwordConfirm = passwordConfirmField.text.toString()

            // Check if any fields are empty
            if (firstname.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                // Check if passwords match
                if (password == passwordConfirm) {
                    // Launch a coroutine to interact with the database
                    lifecycleScope.launch {
                        // Get the UserDao instance from the database
                        val userDao = AppDatabase.getDatabase(this@Register).userDao()

                        // Check if the username already exists in the database
                        val doesUserExist = userDao.doesUsernameExist(username) > 0
                        if (doesUserExist) {
                            // Show a toast if the username is taken
                            runOnUiThread {
                                Toast.makeText(this@Register, "Username already exists", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Create a new User object
                            val user = User(0, firstname, username, password)

                            // Insert the new user into the database
                            userDao.insertUser(user)

                            // Show a toast to indicate the user was created successfully
                            runOnUiThread {
                                Toast.makeText(this@Register, "User created successfully", Toast.LENGTH_SHORT).show()
                            }

                            // Start MainActivity and pass the user to the next screen
                            val intent = Intent(this@Register, MainActivity::class.java)
                            startActivity(intent)
                            finish() // Close the current Register activity
                        }
                    }
                } else {
                    // Show a toast if passwords do not match
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show a toast if any of the required fields are empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
