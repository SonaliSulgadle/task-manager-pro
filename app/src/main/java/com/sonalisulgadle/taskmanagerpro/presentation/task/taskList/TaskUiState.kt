package com.sonalisulgadle.taskmanagerpro.presentation.task.taskList

import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task

sealed class TaskUiState {
    object Loading : TaskUiState()
    object Empty : TaskUiState()
    data class Success(val tasks: List<Task>) : TaskUiState()
    data class Error(val message: String) : TaskUiState()
}
