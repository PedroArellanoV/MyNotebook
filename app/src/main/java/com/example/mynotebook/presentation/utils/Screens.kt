package com.example.mynotebook.presentation.utils

sealed class Screens(val route: String){
    data object MainScreen: Screens("main_screen")
    data object AddEditNotesScreen: Screens("add_edit_notes")
    data object AddEditTaskScreen: Screens("add_edit_task")
}