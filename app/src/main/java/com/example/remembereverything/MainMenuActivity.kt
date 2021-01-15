package com.example.remembereverything

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val backButton = findViewById<FloatingActionButton>(R.id.go_to_dashboard_button)
        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val addTaskButton = findViewById<Button>(R.id.addTask)
        addTaskButton.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        val scheduleButton = findViewById<Button>(R.id.schedule)
        scheduleButton.setOnClickListener{
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }

        val addFaceButton = findViewById<Button>(R.id.face)
        addFaceButton.setOnClickListener{
            val intent = Intent(this, AddFaceActivity::class.java)
            startActivity(intent)
        }
    }
}