package com.example.mynotebook.presentation.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotebook.presentation.main.MainScreen
import com.example.mynotebook.presentation.add_edit_notes.AddEditNoteScreen
import com.example.mynotebook.presentation.add_edit_task.AddEditTaskScreen

@Composable
fun Navigation(navController: NavController){
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.MainScreen.route){
        composable(route = Screens.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(route = Screens.AddEditNotesScreen.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditNoteScreen(navController = navController)
        }
        composable(route = Screens.AddEditTaskScreen.route + "?taskId={taskId}",
            arguments = listOf(
                navArgument(
                    name = "taskId"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditTaskScreen(navController = navController)
        }
    }
}