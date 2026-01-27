package com.sonalisulgadle.taskmanagerpro.presentation.task.taskList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMTaskCard
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    onTaskCardClick: (String) -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is TaskEvent.ShowUndoDelete -> {
                    val result = snackBarHostState.showSnackbar(
                        message = context.getString(R.string.label_task_deleted),
                        actionLabel = context.getString(R.string.action_undo_delete),
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.undoDelete(event.task)
                    }
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->

        if (showDialog) {
            AddTaskDialog(
                onDismiss = { showDialog = false },
                onAdd = { title, description ->
                    viewModel.addTask(title, description)
                }
            )
        }

        when (val state = uiState) {
            TaskUiState.Loading -> LoadingStateView()

            is TaskUiState.Success -> {
                val tasks = state.tasks
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    item {
                        FilterBarView(
                            currentFilter = state.filter,
                            currentSort = state.sort,
                            onFilterSelected = viewModel::setFilter,
                            onSortSelected = viewModel::setSortingMethod
                        )
                    }
                    items(tasks) { task ->
                        TMTaskCard(
                            task = task,
                            onToggle = { viewModel.toggleCompleted(task) },
                            onDelete = { viewModel.deleteTask(task) },
                            onCardClick = { taskId ->
                                onTaskCardClick(taskId)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            is TaskUiState.Empty -> {
                Column(modifier = modifier.padding(16.dp)) {
                    FilterBarView(
                        currentFilter = state.filter,
                        currentSort = state.sort,
                        onFilterSelected = viewModel::setFilter,
                        onSortSelected = viewModel::setSortingMethod
                    )
                    EmptyStateView({ showDialog = true })
                }
            }

            is TaskUiState.Error -> ErrorStateView(
                state.message,
                onRetry = { viewModel.retry() }
            )
        }
    }
}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank()) {
                        onAdd(title, description.ifBlank { null })
                        onDismiss()
                    }
                }
            ) { Text(stringResource(R.string.action_add)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        },
        title = { Text(stringResource(R.string.text_new_task)) },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.text_title)) }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.text_description)) }
                )
            }
        }
    )
}

@Composable
@PreviewLightDark
fun PreviewTaskListScreen() {
    TaskManagerProTheme {
        TaskListScreen(onTaskCardClick = {})
    }
}