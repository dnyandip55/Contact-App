package com.example.contactmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contacts)

        val fab=findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener{
            val userId = intent.getStringExtra("email")
            val i = Intent(this, MyContact::class.java)
            i.putExtra("userId", userId)
            startActivity(i)
        }

    }
}