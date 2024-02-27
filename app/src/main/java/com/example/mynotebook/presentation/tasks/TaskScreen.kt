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

    LaunchedEffect(viewModel) {
        viewModel.getTasks()
    }

    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(taskList) { task ->
                TaskItem(
                    title = task.title,
                    state = task.state,
                    hour = viewModel.getReminder(task),
                    onCheckedChange = { viewModel.onStateChanged(task) },
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