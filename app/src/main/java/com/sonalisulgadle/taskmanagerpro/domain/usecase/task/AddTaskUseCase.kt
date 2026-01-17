package com.sonalisulgadle.taskmanagerpro.domain.usecase.task

import com.sonalisulgadle.taskmanagerpro.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(title: String, description: String) =
        repository.addTask(title, description)
}