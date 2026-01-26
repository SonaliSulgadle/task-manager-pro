package com.sonalisulgadle.taskmanagerpro.presentation.task.taskList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.AddTaskUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.DeleteTaskUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.ObserveTasksUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.UpdateTaskParams
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    var state: StateFlow<TaskUiState> = _state

    private val _event = MutableSharedFlow<TaskEvent>()
    var event = _event.asSharedFlow()

    init {
        observeTasks()
    }

    internal fun observeTasks() {
        observeTasksUseCase()
            .onEach { tasks ->
                _state.value =
                    if (tasks.isEmpty()) TaskUiState.Empty else TaskUiState.Success(tasks)
            }
            .catch {
                _state.value = TaskUiState.Error(it.message ?: "Something went wrong")
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
            updateTaskUseCase(
                UpdateTaskParams(
                    taskId = task.id,
                    title = task.title,
                    description = task.description,
                    isCompleted = !task.completed
                )
            )
        }
    }

    internal fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task.id)
            _event.emit(
                TaskEvent.ShowUndoDelete(task)
            )
        }
    }

    internal fun undoDelete(task: Task) {
        viewModelScope.launch {
            addTaskUseCase(
                title = task.title,
                description = task.description.orEmpty()
            )
        }
    }

    internal fun retry() {
        _state.value = TaskUiState.Loading
        observeTasks()
    }
}