package com.sonalisulgadle.taskmanagerpro.data.mapper.task

import com.sonalisulgadle.taskmanagerpro.data.entity.task.TaskDto
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task

fun TaskDto.mapToTask(id: String): Task =
    Task(
        id = id,
        title = this.title,
        description = this.description,
        completed = this.completed,
        createdAt = this.createdAt.seconds,
        updatedAt = this.updatedAt.seconds
    )
