package com.sonalisulgadle.taskmanagerpro.presentation.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.domain.model.AppTheme
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMButton
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMTopBarWithBack

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onSignedOut: () -> Unit
) {

    val theme by viewModel.theme.collectAsState()

    Scaffold(
        topBar = {
            TMTopBarWithBack(
                stringResource(R.string.header_settings),
                onNavigateBack = onBackPressed
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.label_theme),
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTheme.entries.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.setTheme(it) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = theme == it,
                        onClick = { viewModel.setTheme(it) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.name.lowercase().replaceFirstChar(Char::uppercase),
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TMButton(
                title = stringResource(R.string.action_sign_out),
                onClick = {
                    viewModel.signOut()
                    onSignedOut()
                }
            )
        }
    }
}