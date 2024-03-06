package com.example.mynotebook.presentation.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotebook.presentation.tasks.composables.TaskItem
import com.example.mynotebook.presentation.utils.Screens

@Composable
fun TaskScreen(
    viewModel: TaskScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val taskList = viewModel.taskList.value
    val refreshKey = viewModel.refreshKey.value

    LaunchedEffect(refreshKey) {
        viewModel.getTasks()
    }

    Scaffold(
        modifier = Modifier.padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(taskList) { task ->
                TaskItem(
                    title = task.title,
                    alarmState = task.alarmState.isActive,
                    alarmDescription = if (task.alarmState.dailyAlarm != null || task.alarmState.calendarAlarm != null) {
                        viewModel.getAlarmDescription(task)
                    } else "" ,
                    daysSelected = task.alarmState.dailyAlarm?.selectedDays ?: emptyList(),
                    onCheckedChange = { viewModel.onStateChanged(task) },
                    description = task.description,
                    modifier = Modifier
                        .padding(8.dp),
                    onCardClicked = {
                        navController.navigate(Screens.AddEditTaskScreen.route + "?taskId=${task.id}")
                    }
                )
            }
        }
    }
}