package com.sonalisulgadle.taskmanagerpro.presentation.task.taskList

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.domain.model.task.TaskFilter
import com.sonalisulgadle.taskmanagerpro.domain.model.task.TaskSort
import com.sonalisulgadle.taskmanagerpro.ui.theme.IndigoPrimary
import com.sonalisulgadle.taskmanagerpro.ui.theme.PastelIndigo

@Composable
fun FilterBarView(
    currentFilter: TaskFilter,
    currentSort: TaskSort,
    onFilterSelected: (TaskFilter) -> Unit,
    onSortSelected: (TaskSort) -> Unit
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TaskFilter.entries.forEach {
                FilterChip(
                    selected = currentFilter == it,
                    onClick = { onFilterSelected(it) },
                    label = {
                        Text(
                            text = it.name,
                            fontFamily = FontFamily.Monospace,
                            color = IndigoPrimary,
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PastelIndigo,
                        selectedLabelColor = IndigoPrimary
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box {
            AssistChip(
                shape = RoundedCornerShape(16.dp),
                onClick = { sortMenuExpanded = true },
                label = {
                    Text(
                        text = stringResource(R.string.label_sort_by, currentSort.name),
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Monospace
                    )
                }
            )
            DropdownMenu(
                expanded = sortMenuExpanded,
                onDismissRequest = { sortMenuExpanded = false }
            ) {
                TaskSort.entries.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.padding(8.dp),
                        text = { Text(text = it.name, fontFamily = FontFamily.Monospace) },
                        onClick = {
                            onSortSelected(it)
                            sortMenuExpanded = false
                        }
                    )
                }
            }
        }
    }
}