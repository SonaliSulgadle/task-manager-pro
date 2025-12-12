package com.sonalisulgadle.taskmanagerpro.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@Composable
fun TMTaskCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = CardDefaults.elevatedShape,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
            )
        )
    }
}


@Composable
@PreviewLightDark
fun PreviewTMTaskCard() {
    TaskManagerProTheme {
        TMTaskCard("Title")
    }
}