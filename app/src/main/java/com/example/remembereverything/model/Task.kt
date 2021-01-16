package com.example.remembereverything.model

import java.io.Serializable

data class Task(
    val id: String = "",
    val taskTitle: String = "",
    val taskDate: String = "",
    val taskTime: String = "",
    val taskNotes: String = "",
    var taskStatus: TaskStatusEnum = TaskStatusEnum.ToDo

) : Serializable {}



