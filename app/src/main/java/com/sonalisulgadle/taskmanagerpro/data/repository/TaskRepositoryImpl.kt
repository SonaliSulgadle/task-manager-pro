package com.sonalisulgadle.taskmanagerpro.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sonalisulgadle.taskmanagerpro.data.entity.task.TaskDto
import com.sonalisulgadle.taskmanagerpro.data.mapper.task.mapToTask
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.domain.repository.TaskRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TaskRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : TaskRepository {
    override fun observeTasks(): Flow<List<Task>> = callbackFlow {
        val listener = tasksRef()
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val tasks = snapshot?.documents?.mapNotNull {
                    it.toObject(TaskDto::class.java)?.mapToTask(it.id)
                }.orEmpty()

                trySend(tasks)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun addTask(title: String, description: String?) {
        val dto = TaskDto(
            title = title,
            description = description,
            completed = false,
            createdAt = Timestamp.now(),
            updatedAt = Timestamp.now()
        )

        tasksRef().add(dto).await()
    }

    override suspend fun updateTask(task: Task) {
        tasksRef().document(task.id)
            .update(
                mapOf(
                    "title" to task.title,
                    "description" to task.description,
                    "completed" to task.completed,
                    "updatedAt" to Timestamp.now()
                )
            ).await()
    }

    override suspend fun deleteTask(taskId: String) {
        tasksRef().document(taskId).delete().await()
    }

    private fun tasksRef(): CollectionReference {
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

        return firestore
            .collection("users")
            .document(uid)
            .collection("tasks")
    }
}