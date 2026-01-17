package com.sonalisulgadle.taskmanagerpro.domain.repository

import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun addTask(title: String, description: String?)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
}