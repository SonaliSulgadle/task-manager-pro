package com.sonalisulgadle.taskmanagerpro.presentation.task.taskList

import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.domain.model.task.TaskFilter
import com.sonalisulgadle.taskmanagerpro.domain.model.task.TaskSort

sealed class TaskUiState {
    object Loading : TaskUiState()
    data class Empty(
        val filter: TaskFilter,
        val sort: TaskSort
    ) : TaskUiState()

    data class Success(
        val tasks: List<Task>,
        val filter: TaskFilter,
        val sort: TaskSort
    ) : TaskUiState()

    data class Error(val message: String) : TaskUiState()
}
