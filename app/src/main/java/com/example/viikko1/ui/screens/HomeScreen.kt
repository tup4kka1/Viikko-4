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
fun HomeScreen(
    taskViewModel: TaskViewModel,
    onGoCalendar: () -> Unit,
    onGoSettings: () -> Unit
) {

    var selectedTask by remember { mutableStateOf<Task?>(null) }
    val tasks = taskViewModel.tasks
        var newTitle by remember { mutableStateOf("") }
    var showDoneOnly by remember { mutableStateOf(false) }

    val visibleTasks = remember(tasks, showDoneOnly) {
        if (showDoneOnly) {
            tasks.filter { it.done }
        } else {
            tasks
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onGoCalendar) {
                Text("Calendar")
            }

            Button(onClick = onGoSettings) {
                Text("Settings")
            }
        }


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                showDoneOnly = !showDoneOnly
            }) {
                Text(if (showDoneOnly) "Show all" else "Show done")
            }

            Button(onClick = {
                taskViewModel.sortByDueDate()
            }) {
                Text("Sort duedate")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            TextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("New task") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newTitle.isNotBlank()) {
                    taskViewModel.addTask(newTitle)
                    newTitle = ""
                }
            }) {
                Text("Add")
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(visibleTasks) { task ->
                Row(
                    modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedTask = task }
                    .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { taskViewModel.toggleDone(task.id) }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = task.title,
                            )
                            Text(
                                text = task.description,
                            )
                        }

                    }

                    Button(
                        onClick = { taskViewModel.removeTask(task.id) }
                    ) {
                        Text("Del")
                    }
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
