package com.sonalisulgadle.taskmanagerpro.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.presentation.task.taskList.TaskListScreen
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onTaskCardClick: (String) -> Unit,
    onSettingClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = onSettingClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            TaskListScreen(
                modifier = Modifier.padding(paddingValues),
                onTaskCardClick = onTaskCardClick
            )
        }
    )
}

@Composable
@PreviewLightDark
fun PreviewHomeScreen() {
    TaskManagerProTheme {
        HomeScreen(onTaskCardClick = {}, onSettingClick = {})
    }
}