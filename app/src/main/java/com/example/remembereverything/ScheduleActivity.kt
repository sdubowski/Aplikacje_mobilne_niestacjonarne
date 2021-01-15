package com.example.remembereverything

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
import com.example.remembereverything.model.Task
import com.example.remembereverything.model.TaskStatusEnum
import com.example.remembereverything.repository.TaskRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule)


        val recyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()

        TaskRepository.databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onDataChange(data: DataSnapshot) {
            val items: ArrayList<Task> = ArrayList()
            data.children.forEach { items.add(it.getValue(Task::class.java)!!) }
            recyclerView.adapter = ListViewAdapter(items)
        }
    })
 }


inner class ListViewAdapter(private val tasks: ArrayList<Task>) : RecyclerView.Adapter<ListViewAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val rowView = layout.inflate(R.layout.task_row, parent, false)
        return TaskViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.titleTextView.text = tasks[position].taskTitle
        holder.dateTextView.text = tasks[position].taskDate
        holder.timeTextView.text = tasks[position].taskTime

        holder.deleteButton
            .setOnClickListener {
                val db = TaskRepository()
                db.delete(tasks[position].id)
            }

        if(tasks[position].taskStatus == TaskStatusEnum.Done){
           holder.realizedBUtton.isEnabled = false
            holder.realizedBUtton.text = "Finished"
        }

        holder.realizedBUtton
            .setOnClickListener {
                tasks[position].taskStatus = TaskStatusEnum.Done
                val db = TaskRepository()
                db.update(tasks[position])
            }
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val titleTextView = view.findViewById<TextView>(R.id.rowTitle)
        val dateTextView = view.findViewById<TextView>(R.id.rowDate)
        val timeTextView = view.findViewById<TextView>(R.id.rowTime)
        val deleteButton = view.findViewById<FloatingActionButton>(R.id.deleteTaskButton)
        val realizedBUtton = view.findViewById<Button>(R.id.realizedButton)
    }
}
}