package com.example.remembereverything.repository

import com.example.remembereverything.model.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase.getInstance


class TaskRepository {

    companion object {
        var databaseRef: DatabaseReference =
            getInstance("https://remembereverything-a0b2b-default-rtdb.firebaseio.com/").getReference("Tasks")
    }

    fun save(task: Task) {
        databaseRef.child("${task.id}").setValue(task)
    }

    fun delete(id: String) {
        databaseRef.child(id).removeValue()
    }

    fun update(task: Task) {
        databaseRef.child("${task.id}").setValue(task)
    }

}