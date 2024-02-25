package com.example.contactmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignIn : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var userName:TextInputEditText
    private  lateinit var  password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        databaseReference = FirebaseDatabase.getInstance().getReference("User")

        val signInBtn = findViewById<Button>(R.id.signInBtn)
         userName = findViewById(R.id.userName)
        password = findViewById(R.id.password)

        signInBtn.setOnClickListener {
            val uniqueId = userName.text.toString()
            val password = password.text.toString()

            if (uniqueId.isNotEmpty() && password.isNotEmpty()) {
                readData(uniqueId, password)
            } else {
                Toast.makeText(this, "Please enter user ID and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(uniqueId: String, enteredPassword: String) {
        val encodedEmail = uniqueId.replace(".", ",")

        databaseReference.child(encodedEmail).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val email = dataSnapshot.child("email").value?.toString()
                val correctPassword = dataSnapshot.child("password").value?.toString()

                if (enteredPassword == correctPassword && email == uniqueId) {
                    val userId = dataSnapshot.child("email").value?.toString()
                    if (!userId.isNullOrEmpty()) {
                        val addContacts = Intent(this, AddContacts::class.java)
                        addContacts.putExtra("email", userId)
                        startActivity(addContacts)
                        userName.text = null
                        password.text=null



                    } else {
                        Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
        }
    }

}
