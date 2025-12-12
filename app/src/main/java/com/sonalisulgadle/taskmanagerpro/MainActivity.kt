package com.sonalisulgadle.taskmanagerpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sonalisulgadle.taskmanagerpro.presentation.navigation.NavGraph
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerProTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavGraph()
                }
            }
        }
    }
}
