package com.sonalisulgadle.taskmanagerpro.domain.repository

import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.UpdateTaskParams
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun addTask(title: String, description: String?)
    suspend fun updateTask(params: UpdateTaskParams)
    suspend fun deleteTask(taskId: String)
    suspend fun getTaskById(taskId: String): Task?
}