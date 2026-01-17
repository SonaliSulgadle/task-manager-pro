package com.sonalisulgadle.taskmanagerpro.data.entity.task

import com.google.firebase.Timestamp

data class TaskDto(
    val title: String,
    val description: String? = null,
    val completed: Boolean = false,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)