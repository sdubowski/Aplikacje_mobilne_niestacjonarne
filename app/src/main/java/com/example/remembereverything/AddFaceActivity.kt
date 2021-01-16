package com.example.remembereverything

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remembereverything.model.Person
import com.example.remembereverything.model.Task
import com.example.remembereverything.model.TaskStatusEnum
import com.example.remembereverything.repository.PersonRepository
import com.example.remembereverything.repository.TaskRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class AddFaceActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_face)


        val recyclerView = findViewById<RecyclerView>(R.id.faceRecycler)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()

        val savePersonButton = findViewById<Button>(R.id.savePerson)
        savePersonButton.setOnClickListener{
            val db = PersonRepository()
            val personFirstName = findViewById<TextView>(R.id.firstName)
            val personLastName = findViewById<TextView>(R.id.lastName)
            val personAge = findViewById<TextView>(R.id.age)
            val personRelationship = findViewById<TextView>(R.id.relationship)

            val person = Person(
                Date().time.toString(),
                personFirstName?.text.toString(),
                personLastName?.text.toString(),
                personAge?.text.toString().toInt(),
                personRelationship?.text.toString()
            )

            db.save(person)
            personFirstName.text = ""
            personLastName.text = ""
            personAge.text = ""
            personRelationship.text = ""
        }

        PersonRepository.databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(data: DataSnapshot) {
                val items: ArrayList<Person> = ArrayList()
                data.children.forEach { items.add(it.getValue(Person::class.java)!!) }
                recyclerView.adapter = ListViewAdapter(items)
            }
        })
    }


     inner class ListViewAdapter(private val persons: ArrayList<Person>) : RecyclerView.Adapter<ListViewAdapter.TaskViewHolder>() {
         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
             val layout = LayoutInflater.from(parent.context)
             val rowView = layout.inflate(R.layout.face_row, parent, false)
             return TaskViewHolder(rowView)
         }

         override fun getItemCount(): Int {
             return persons.size
         }

         override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
             holder.firstNameTextView.text = persons[position].firstName
             holder.lastNameTextView.text = persons[position].lastName
             holder.ageTextView.text = persons[position].age.toString()
             holder.relationshipTextView.text = persons[position].relationship

             holder.deleteButton
                 .setOnClickListener {
                     val db = PersonRepository()
                     db.delete(persons[position].id)
                 }
         }

         inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){
             val firstNameTextView = view.findViewById<TextView>(R.id.firstNameRow)
             val lastNameTextView = view.findViewById<TextView>(R.id.lastNameRow)
             val ageTextView = view.findViewById<TextView>(R.id.ageRow)
             val deleteButton = view.findViewById<FloatingActionButton>(R.id.deletePersonButton)
             val relationshipTextView = view.findViewById<TextView>(R.id.relationshipRow)
         }
     }
}