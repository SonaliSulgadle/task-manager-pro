package com.sonalisulgadle.taskmanagerpro.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@Composable
fun TMTaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = task.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace,
                    )
                )
                task.description?.let {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = it,
                        color = MaterialTheme.colorScheme.secondary,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Monospace,
                        )
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}

internal val task = Task(
    id = "1",
    title = "Title",
    description = "This is a sample description",
    completed = false,
    createdAt = 12121221,
    updatedAt = 21212121
)

@Composable
@PreviewLightDark
fun PreviewTMTaskCard() {
    TaskManagerProTheme {
        TMTaskCard(task = task, onToggle = {}, onDelete = {})
    }
}