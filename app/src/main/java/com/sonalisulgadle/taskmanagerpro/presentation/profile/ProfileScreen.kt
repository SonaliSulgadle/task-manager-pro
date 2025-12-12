package com.sonalisulgadle.taskmanagerpro.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMOutlinedButton
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMTopBar
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = { TMTopBar("Profile") }
    ) { }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TMOutlinedButton(
            "Edit Profile",
            {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        TMOutlinedButton(
            "Notifications",
            {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        TMOutlinedButton(
            "Logout",
            {}
        )
    }
}

@Composable
@PreviewLightDark
fun PreviewProfileScreen() {
    TaskManagerProTheme {
        ProfileScreen()
    }
}