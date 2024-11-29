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

        val firstnameField: EditText = findViewById(R.id.firstname_create_input)
        val usernameField: EditText = findViewById(R.id.username_create_input)
        val passwordField: EditText = findViewById(R.id.password_create_input)
        val passwordConfirmField: EditText = findViewById(R.id.password_confirm_input)
        val createButton: Button = findViewById(R.id.create_btn)

        createButton.setOnClickListener {
            val firstname = firstnameField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val passwordConfirm = passwordConfirmField.text.toString()

            if (firstname.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                if (password == passwordConfirm) {
                    lifecycleScope.launch {
                        val userDao = AppDatabase.getDatabase(this@Register).userDao()
                        val doesUserExist = userDao.doesUsernameExist(username) > 0
                        if (doesUserExist) {
                            runOnUiThread {
                                Toast.makeText(this@Register, "Username already exists", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val user = User(0, firstname, username, password)
                            userDao.insertUser(user)
                            runOnUiThread {
                                Toast.makeText(this@Register, "User created successfully", Toast.LENGTH_SHORT).show()
                            }
                            val intent = Intent(this@Register, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
