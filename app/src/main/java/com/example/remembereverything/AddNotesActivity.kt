package com.example.remembereverything

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.adding_notes)
        val et = findViewById<EditText>(R.id.notes_label)
        val intent = intent

        val deleteNotes = findViewById<FloatingActionButton>(R.id.delete_note_button)
        deleteNotes.setOnClickListener {
            et.text = null
        }


        val saveNotes = findViewById<FloatingActionButton>(R.id.add_note_button)
        saveNotes.setOnClickListener {
            val resultIntent = Intent(this, AddTaskActivity::class.java)
            val notes = et.editableText.toString();
            intent.putExtra("notes", notes)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

}