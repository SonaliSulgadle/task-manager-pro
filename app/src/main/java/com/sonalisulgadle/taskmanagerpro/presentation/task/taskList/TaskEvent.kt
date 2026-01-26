package com.sonalisulgadle.taskmanagerpro.presentation.task.taskList

import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task

sealed interface TaskEvent {
    data class ShowUndoDelete(val task: Task) : TaskEvent
}