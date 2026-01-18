package com.sonalisulgadle.taskmanagerpro.presentation.task

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMTaskCard
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text(state.error ?: "")
            else -> {
                LazyColumn {
                    items(state.tasks) { task ->
                        TMTaskCard(
                            task = task,
                            onToggle = { viewModel.toggleCompleted(task) },
                            onDelete = { viewModel.deleteTask(task.id) })
                    }
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PreviewTaskListScreen() {
    TaskManagerProTheme {
        TaskListScreen()
    }
}