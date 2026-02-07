package com.example.viikko1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.viikko1.domain.*

class TaskViewModel : ViewModel() {

    private var _tasks by mutableStateOf(listOf<Task>())
    val tasks: List<Task> get() = _tasks

    init {
        _tasks = taskList
    }

    fun addTask(title: String) {
        val newId = (_tasks.maxOfOrNull { it.id } ?: 0) + 1
        val task = Task(
            id = newId,
            title = title,
            description = "",
            priority = 0,
            dueDate = "",
            done = false
        )
        _tasks = addTask(_tasks, task)
    }

    fun toggleDone(id: Int) {
        _tasks = toggleDone(_tasks, id)
    }

    fun removeTask(id: Int) {
        _tasks = removeTask(_tasks, id)
    }

    fun sortByDueDate() {
        _tasks = sortByDueDate(_tasks)
    }

    fun updateTask(updatedTask: Task) {
        _tasks = _tasks.map { task ->
            if (task.id == updatedTask.id) updatedTask else task
        }
    }

}
