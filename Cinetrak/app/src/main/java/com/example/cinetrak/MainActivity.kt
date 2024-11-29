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

        val usernameField: EditText = findViewById(R.id.username_input)
        val passwordField: EditText = findViewById(R.id.password_input)
        val loginButton: Button = findViewById(R.id.login_btn)
        val registerButton: Button = findViewById(R.id.register_btn)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val userDao = AppDatabase.getDatabase(this@MainActivity).userDao()
                    val user = userDao.getUser(username, password)
                    if (user != null) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(this@MainActivity, Homepage::class.java)
                        intent.putExtra("USER_FIRST_NAME", user.firstname)
                        startActivity(intent)
                        finish()
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
