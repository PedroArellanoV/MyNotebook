package com.example.mynotebook.presentation.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotebook.presentation.tasks.composables.TaskItem

@Composable
fun TaskScreen(
    viewModel: TaskScreenViewModel = hiltViewModel(),
    navController: NavController
){
    val taskList = viewModel.taskList.value

    LaunchedEffect(viewModel){
        viewModel.getTasks()
    }

    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            items(taskList){task ->
                TaskItem(
                    title = task.title,
                    state = task.state,
                    id = task.id ?: -1,
                    modifier = Modifier
                        .clickable {  })
            }
        }
    }
}