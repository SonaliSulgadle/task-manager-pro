package com.sonalisulgadle.taskmanagerpro.presentation.task.editTask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.GetTaskByIdUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.UpdateTaskParams
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val taskId: String = savedStateHandle["taskId"] ?: error("taskID missing")

    var uiState by mutableStateOf(EditTaskUiState())
        private set

    init {
        loadTask()
    }

    internal fun loadTask() {
        viewModelScope.launch {
            runCatching {
                getTaskByIdUseCase(taskId)
            }.onSuccess { task ->
                uiState = uiState.copy(
                    title = task?.title.orEmpty(),
                    description = task?.description.orEmpty(),
                    isCompleted = task?.completed == true,
                    isLoading = false
                )
            }.onFailure {
                uiState = uiState.copy(
                    isLoading = false,
                    error = it.message
                )
            }
        }
    }

    internal fun onTitleChange(newValue: String) {
        uiState = uiState.copy(title = newValue)
    }

    internal fun onDescriptionChange(newValue: String) {
        uiState = uiState.copy(description = newValue)
    }

    internal fun onToggleChange(newValue: Boolean) {
        uiState = uiState.copy(isCompleted = newValue)
    }

    internal fun save(onSuccess: () -> Unit) {
        if (uiState.title.isBlank()) return

        viewModelScope.launch {
            uiState = uiState.copy(isSaving = true)

            runCatching {
                updateTaskUseCase.invoke(
                    UpdateTaskParams(
                        taskId = taskId,
                        title = uiState.title.trim(),
                        description = uiState.description.takeIf { it.isNotBlank() },
                        isCompleted = uiState.isCompleted
                    )
                )
            }.onSuccess {
                onSuccess()
            }.onFailure {
                uiState = uiState.copy(
                    isSaving = false,
                    error = it.message
                )
            }
        }
    }

}