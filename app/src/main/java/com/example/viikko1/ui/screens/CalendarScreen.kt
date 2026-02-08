package com.example.viikko1.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.Task
import com.example.viikko1.viewmodel.TaskViewModel

@Composable
fun CalendarScreen(
    taskViewModel: TaskViewModel,
    onBack: () -> Unit
) {
    val tasks = taskViewModel.tasks
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    val grouped = remember(tasks) {
        tasks.groupBy { it.dueDate }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Back to list")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            grouped.forEach { (date, tasksForDay) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(tasksForDay) { task ->
                    Text(
                        text = "• ${task.title}",
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp)
                            .clickable { selectedTask = task }
                    )
                }
            }
        }

        if (selectedTask != null) {
            DetailDialog(
                task = selectedTask!!,
                onDismiss = { selectedTask = null },
                onSave = {
                    taskViewModel.updateTask(it)
                    selectedTask = null
                },
                onDelete = {
                    taskViewModel.removeTask(it.id)
                    selectedTask = null
                }
            )
        }
    }
}
