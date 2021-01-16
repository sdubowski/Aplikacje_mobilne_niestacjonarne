package com.example.remembereverything.repository

import com.example.remembereverything.model.Person
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PersonRepository {

    companion object {
        var databaseRef: DatabaseReference =
            FirebaseDatabase.getInstance("https://remembereverything-a0b2b-default-rtdb.firebaseio.com/")
                .getReference("Persons")
    }

    fun save(person: Person) {
        databaseRef.child("${person.id}").setValue(person)
    }

    fun delete(id: String) {
        databaseRef.child(id).removeValue()
    }
}