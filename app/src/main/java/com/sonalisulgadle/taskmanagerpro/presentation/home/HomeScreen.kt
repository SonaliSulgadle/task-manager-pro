package com.sonalisulgadle.taskmanagerpro.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMTaskCard
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMTopBar
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TMTopBar(stringResource(R.string.home_title))
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // TODO - temp data
                items(4) {
                    TMTaskCard(
                        "title"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
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