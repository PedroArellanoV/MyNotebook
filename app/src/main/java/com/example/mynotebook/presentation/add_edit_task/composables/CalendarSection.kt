package com.example.mynotebook.presentation.add_edit_task.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSection(
    datePickerState: DatePickerState
) {
    Column {
        DatePicker(state = datePickerState, modifier = Modifier.padding(8.dp))
    }
}