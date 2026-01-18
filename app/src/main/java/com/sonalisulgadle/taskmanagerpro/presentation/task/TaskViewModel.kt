package com.sonalisulgadle.taskmanagerpro.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.AddTaskUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.DeleteTaskUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.ObserveTasksUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    observeTasksUseCase: ObserveTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TaskUiState())
    var state: StateFlow<TaskUiState> = _state

    init {
        observeTasksUseCase()
            .onEach {
                _state.value = TaskUiState(tasks = it, isLoading = false)
            }
            .catch {
                _state.value = TaskUiState(error = it.message)
            }
            .launchIn(viewModelScope)
    }

    internal fun addTask(title: String, description: String?) {
        viewModelScope.launch {
            addTaskUseCase(title, description ?: "")
        }
    }

    internal fun toggleCompleted(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(completed = !task.completed))
        }
    }

    internal fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }
}