package com.example.cs481

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import com.example.cs481.R

class DataEntryActivity : AppCompatActivity() {

    private val database = FirebaseFirestore.getInstance()

    private val userid: String
        get() = findViewById<EditText>(R.id.userid).text.toString()
    private val id: String
        get() = findViewById<EditText>(R.id.id).text.toString()
    private val title: String
        get() = findViewById<EditText>(R.id.todo).text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val enterbutton = findViewById<Button>(R.id.data_button)

        enterbutton.setOnClickListener() {
            if (userid.isEmpty() || id.isEmpty() || title.isEmpty()) {
                val empty =
                    Toast.makeText(applicationContext, "Fill in all fields.", Toast.LENGTH_SHORT)
                empty.show()
            } else {
                val collect = database.collection("midterm")
                val todoTask: MutableMap<String, Any> = HashMap()
                todoTask["userid"] = userid
                todoTask["id"] = id
                todoTask["title"] = title
                todoTask["complete"] = "false"

                collect.add(todoTask).addOnSuccessListener {
                    val added =
                        Toast.makeText(applicationContext, "Data Added!", Toast.LENGTH_SHORT)
                    added.show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }
}