package com.example.cinetrak

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.cinetrak.data.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements (EditTexts for username/password, Buttons for login and register)
        val usernameField: EditText = findViewById(R.id.username_input)
        val passwordField: EditText = findViewById(R.id.password_input)
        val loginButton: Button = findViewById(R.id.login_btn)
        val registerButton: Button = findViewById(R.id.register_btn)

        // Set an OnClickListener for the login button
        loginButton.setOnClickListener {
            val username = usernameField.text.toString() // Get the username input
            val password = passwordField.text.toString() // Get the password input

            // Check if both username and password are provided
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Start a coroutine to interact with the database asynchronously
                lifecycleScope.launch {
                    // Get the userDao from the database
                    val userDao = AppDatabase.getDatabase(this@MainActivity).userDao()
                    // Query the userDao to check if a user with the provided username and password exists
                    val user = userDao.getUser(username, password)

                    // If a matching user is found, log them in
                    if (user != null) {
                        runOnUiThread {
                            // Show a successful login message
                            Toast.makeText(this@MainActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        }
                        // Create an Intent to navigate to the Homepage activity
                        val intent = Intent(this@MainActivity, Homepage::class.java)
                        intent.putExtra("USER_FIRST_NAME", user.firstname) // Pass the user's first name to the Homepage
                        startActivity(intent) // Start the Homepage activity
                        finish() // Finish the current activity to prevent the user from returning to the login screen
                    } else {
                        // If no matching user is found, show an error message
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                // If either the username or password field is empty, show an error message
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Set an OnClickListener for the register button
        registerButton.setOnClickListener {
            // If the user clicks the register button, navigate to the Register activity
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
