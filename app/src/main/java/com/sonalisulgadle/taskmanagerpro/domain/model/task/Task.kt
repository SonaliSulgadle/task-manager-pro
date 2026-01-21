package com.sonalisulgadle.taskmanagerpro.domain.model.task

data class Task(
    val id: String,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)