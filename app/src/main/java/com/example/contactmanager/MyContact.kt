package com.example.contactmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyContact : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private var userId: String? = null
    private lateinit var nameEditText: EditText
    private lateinit var sirnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_contact)

        // Retrieve userId from intent extras
        userId = intent.getStringExtra("userId")

        // Check if userId is not null
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            finish() // Finish activity if userId is null
            return
        }

        // Encode the email
        val encodedEmail = userId!!.replace(".", ",")

        // Setup database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(encodedEmail).child("Contacts")

        // Initialize views
        val btnSave = findViewById<Button>(R.id.btnSave)
         nameEditText = findViewById(R.id.name)
         sirnameEditText = findViewById(R.id.sirname)
        phoneEditText = findViewById(R.id.phone)
         emailEditText = findViewById(R.id.email)

        // Save button click listener
        btnSave.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val sirname = sirnameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            if (name.isNotEmpty() && sirname.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                saveContact(name, sirname, phone, email)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun saveContact(name: String, sirname: String, phone: String, email: String) {
        val contactId = databaseReference.push().key
        val contact = Contact(contactId, name, sirname, phone, email)

        if (contactId != null) {
            databaseReference.child(contactId).setValue(contact)
                .addOnSuccessListener {
                    Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show()

                    nameEditText.text=null
                    sirnameEditText.text=null
                    phoneEditText.text=null
                    emailEditText.text=null

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save contact", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Failed to generate contact ID", Toast.LENGTH_SHORT).show()
        }
    }
}
