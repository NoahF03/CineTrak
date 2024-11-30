package com.example.cinetrak

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        findViewById<ImageButton>(R.id.homeButton2).setOnClickListener {
            // Navigate to Home
            navigateToHome()
        }

        findViewById<ImageButton>(R.id.userListButton2).setOnClickListener {
            // Navigate to User List
            navigateToUserList()
        }


    }
    private fun navigateToHome() {
        // You can navigate to a home screen or perform any action
        val intent = Intent(this, Homepage::class.java)
        startActivity(intent)
    }

    // Navigate to the User List screen
    private fun navigateToUserList() {
        // Navigate to the user list screen
        val intent = Intent(this, UserListActivity::class.java)
        startActivity(intent)
    }
}
