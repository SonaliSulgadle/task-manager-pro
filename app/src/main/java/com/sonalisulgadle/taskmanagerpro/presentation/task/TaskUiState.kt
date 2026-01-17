package com.sonalisulgadle.taskmanagerpro.presentation.task

import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
