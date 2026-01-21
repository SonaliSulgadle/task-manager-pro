package com.sonalisulgadle.taskmanagerpro.data.mapper.task

import com.google.firebase.Timestamp
import com.sonalisulgadle.taskmanagerpro.data.entity.task.TaskDto
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import java.util.Date

fun TaskDto.mapToTask(id: String): Task =
    Task(
        id = id,
        title = this.title,
        description = this.description,
        completed = this.completed,
        createdAt = this.createdAt.seconds,
        updatedAt = this.updatedAt.seconds
    )

fun Task.mapToTaskDto(): TaskDto =
    TaskDto(
        title = this.title,
        description = this.description,
        completed = this.completed,
        createdAt = Timestamp(Date(this.createdAt)),
        updatedAt = Timestamp(Date(this.updatedAt))
    )