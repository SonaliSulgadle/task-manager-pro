package com.sonalisulgadle.taskmanagerpro.domain.usecase.task

import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.domain.repository.TaskRepository

class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(params: UpdateTaskParams) =
        repository.updateTask(
            Task(
                id = params.taskId,
                title = params.title,
                description = params.description,
                completed = params.isCompleted,
                updatedAt = System.currentTimeMillis()
            )
        )
}

data class UpdateTaskParams(
    val taskId: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean
)