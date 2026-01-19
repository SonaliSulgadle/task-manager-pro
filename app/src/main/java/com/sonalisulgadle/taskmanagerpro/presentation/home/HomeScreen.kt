package com.sonalisulgadle.taskmanagerpro.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMTopBar
import com.sonalisulgadle.taskmanagerpro.presentation.task.TaskListScreen
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TMTopBar(title = stringResource(R.string.home_title))
        },
        content = { paddingValues ->
            TaskListScreen(modifier = Modifier.padding(paddingValues))
        }
    )
}

@Composable
@PreviewLightDark
fun PreviewHomeScreen() {
    TaskManagerProTheme {
        HomeScreen()
    }
}