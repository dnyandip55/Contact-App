package com.example.contactmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        lateinit var database: DatabaseReference

        val etName=findViewById<TextInputEditText>(R.id.etName)
//        val etId=findViewById<TextInputEditText>(R.id.etId)
        val etEmail=findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword=findViewById<TextInputEditText>(R.id.etPassword)
        val button1=findViewById<Button>(R.id.button1)

        button1.setOnClickListener{

            val name=etName.text.toString()
//            val uniqueId=etId.text.toString()
            val email=etEmail.text.toString()
            val password=etPassword.text.toString()

            val user=User(name,email,password)

            val encodedEmail = email.replace(".", ",")
            database= FirebaseDatabase.getInstance().getReference("User")
            database.child(encodedEmail).setValue(user).addOnSuccessListener {
                Toast.makeText(this,"User Registered now Click to Login", Toast.LENGTH_SHORT).show()
                etName.text=null
//                etId.text=null
                etEmail.text=null
                etPassword.text=null
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to Register",Toast.LENGTH_SHORT).show()
            }

        }
        val signIn=findViewById<Button>(R.id.signInActivity)
        signIn.setOnClickListener{

            val i=Intent(this,SignIn::class.java)
            startActivity(i)
        }



    }
}