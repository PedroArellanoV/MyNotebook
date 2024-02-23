package com.example.mynotebook.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flag
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ButtonState(
    val imageVector: ImageVector,
    val contentDescription: String,
    val onClickEvent: ()-> Unit
) {
    data class NotesScreen(
        val navigateToNotes: () -> Unit
    ) : ButtonState(
        imageVector = Icons.Default.Add,
        contentDescription = "Add Note",
        onClickEvent = navigateToNotes
    )

    data class TaskScreen(
        val navigateToTask: () -> Unit
    ) : ButtonState(
        imageVector = Icons.Default.Delete,
        contentDescription = "Add Note",
        onClickEvent = navigateToTask
    )

    data class CalendarScreen(
        val navigateToCalendar: ()-> Unit
    ) : ButtonState(
        imageVector = Icons.Default.Flag,
        contentDescription = "Add Note",
        onClickEvent = navigateToCalendar
    )
}