package com.sonalisulgadle.taskmanagerpro.presentation.task.editTask

data class EditTaskUiState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val isCompleted: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
)