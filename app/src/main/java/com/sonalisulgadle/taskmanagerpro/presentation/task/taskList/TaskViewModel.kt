package com.sonalisulgadle.taskmanagerpro.presentation.task.taskList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.domain.model.task.TaskFilter
import com.sonalisulgadle.taskmanagerpro.domain.model.task.TaskSort
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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

    private val filter = MutableStateFlow(TaskFilter.ALL)
    private val sort = MutableStateFlow(TaskSort.CREATED_DATE)

    init {
        observeTasks()
    }

    internal fun observeTasks() {

        combine(
            observeTasksUseCase(),
            filter,
            sort
        ) { tasks, filter, sort ->

            val filtered = when (filter) {
                TaskFilter.ALL -> tasks
                TaskFilter.ACTIVE -> tasks.filter { !it.completed }
                TaskFilter.COMPLETED -> tasks.filter { it.completed }
            }

            val sorted = when (sort) {
                TaskSort.CREATED_DATE -> filtered.sortedByDescending { it.createdAt }
                TaskSort.UPDATED_DATE -> filtered.sortedByDescending { it.updatedAt }
                TaskSort.TITLE -> filtered.sortedBy { it.title.lowercase() }
            }

            if (sorted.isEmpty()) {
                TaskUiState.Empty(
                    filter = filter,
                    sort = sort
                )
            } else {
                TaskUiState.Success(
                    tasks = sorted,
                    filter = filter,
                    sort = sort
                )
            }
        }
            .catch { _state.value = TaskUiState.Error(it.message ?: "Something went wrong") }
            .onStart { _state.value = TaskUiState.Loading }
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    internal fun addTask(title: String, description: String?) {
        viewModelScope.launch {
            addTaskUseCase(title, description.orEmpty())
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

    internal fun setFilter(filter: TaskFilter) {
        this.filter.value = filter
    }

    internal fun setSortingMethod(sort: TaskSort) {
        this.sort.value = sort
    }
}