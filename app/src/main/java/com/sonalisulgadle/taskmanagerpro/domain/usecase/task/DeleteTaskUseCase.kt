package com.sonalisulgadle.taskmanagerpro.domain.usecase.task

import com.sonalisulgadle.taskmanagerpro.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: String) = repository.deleteTask(taskId)
}