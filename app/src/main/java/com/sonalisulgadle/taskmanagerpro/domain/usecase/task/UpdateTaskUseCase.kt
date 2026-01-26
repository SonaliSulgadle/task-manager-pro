package com.sonalisulgadle.taskmanagerpro.domain.usecase.task

import com.sonalisulgadle.taskmanagerpro.domain.repository.TaskRepository

class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(params: UpdateTaskParams) =
        repository.updateTask(params)
}

data class UpdateTaskParams(
    val taskId: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean
)