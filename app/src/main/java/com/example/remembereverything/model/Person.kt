package com.example.remembereverything.model

import java.io.Serializable

class Person (val id: String = "",
              val firstName: String = "",
              val lastName: String = "",
              val age: Int = 0,
              val relationship: String = ""
) : Serializable {}
