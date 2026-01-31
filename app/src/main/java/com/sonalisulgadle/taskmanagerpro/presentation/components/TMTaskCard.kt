package com.sonalisulgadle.taskmanagerpro.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonalisulgadle.taskmanagerpro.domain.model.task.Task
import com.sonalisulgadle.taskmanagerpro.ui.theme.DarkOnPrimary
import com.sonalisulgadle.taskmanagerpro.ui.theme.PastelIndigo
import com.sonalisulgadle.taskmanagerpro.ui.theme.PastelMintStrong
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@Composable
fun TMTaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onCardClick: (String) -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (task.completed)
            PastelMintStrong
        else
            PastelIndigo
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                onCardClick(task.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.secondary
                )
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = task.title,
                    color = DarkOnPrimary,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                    )
                )
                task.description?.let {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = it,
                        color = DarkOnPrimary,
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
        TMTaskCard(
            task = task,
            onToggle = {},
            onDelete = {},
            onCardClick = {})
    }
}