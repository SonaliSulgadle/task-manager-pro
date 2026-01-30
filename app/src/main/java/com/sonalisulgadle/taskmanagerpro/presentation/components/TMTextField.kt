package com.sonalisulgadle.taskmanagerpro.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun TMOutlinedTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        placeholder = {
            Text(
                text = placeholder,
                modifier = Modifier.padding(4.dp),
                fontFamily = FontFamily.Monospace
            )
        },
        onValueChange = onValueChange,
        singleLine = true,
    )
}