package com.example.remembereverything

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.remembereverything.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.remembereverything.repository.TaskRepository
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        var dateFromCalendar = findViewById<TextView>(R.id.date_text)
        var timeFromWatch = findViewById<TextView>(R.id.time_text)
        val title = findViewById<TextView>(R.id.task_title)

        val dateButton = findViewById<Button>(R.id.select_date)
        dateButton.setOnClickListener {
            handleDateButton()
            dateFromCalendar = findViewById<TextView>(R.id.date_text)

        }

        val timeButton = findViewById<Button>(R.id.choose_time)
        timeButton.setOnClickListener {
            handleTimeButton()
            timeFromWatch = findViewById<TextView>(R.id.time_text)
        }

        val addNotes = findViewById<FloatingActionButton>(R.id.add_notes)
        addNotes.setOnClickListener {
            val intent = Intent(this, AddNotesActivity::class.java)
            startActivityForResult(intent, 1)
        }

        val saveTask = findViewById<Button>(R.id.save_task_button)
        saveTask.setOnClickListener {
            val db = TaskRepository()
            val task = Task(
                Date().time.toString(),
                title?.text.toString(),
                dateFromCalendar?.text.toString(),
                timeFromWatch?.text.toString(),
                "notes"
            )
            db.save(task)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun handleDateButton() {
        val calendar: Calendar = Calendar.getInstance()
        val YEAR: Int = calendar.get(Calendar.YEAR)
        val MONTH: Int = calendar.get(Calendar.MONTH)
        val DATE: Int = calendar.get(Calendar.DATE)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
                val calendar1: Calendar = Calendar.getInstance()
                calendar1.set(Calendar.YEAR, year)
                calendar1.set(Calendar.MONTH, month)
                calendar1.set(Calendar.DATE, date)
                val dateText: String =
                    DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString()
                val dateTextView = findViewById<TextView>(R.id.date_text)
                dateTextView.text = dateText;
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }

    private fun handleTimeButton() {
        val calendar = Calendar.getInstance()
        val HOUR = calendar[Calendar.HOUR]
        val MINUTE = calendar[Calendar.MINUTE]
        val is24HourFormat = DateFormat.is24HourFormat(this)
        val timePickerDialog =
            TimePickerDialog(this, OnTimeSetListener { timePicker, hour, minute ->
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.HOUR] = hour
                calendar1[Calendar.MINUTE] = minute
                val dateText = DateFormat.format("h:mm a", calendar1).toString()
                val timeTextView = findViewById<TextView>(R.id.time_text)
                timeTextView.text = dateText
            }, HOUR, MINUTE, is24HourFormat)
        timePickerDialog.show()
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                if (data != null) {
                    val notes = data.getStringExtra("notes").toString()
                }

        }
    }

}