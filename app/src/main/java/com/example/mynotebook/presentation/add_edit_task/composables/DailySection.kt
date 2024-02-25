package com.example.mynotebook.presentation.add_edit_task.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotebook.presentation.add_edit_task.utils.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailySection(
    toggleDaySelection:(DayOfWeek)->Unit,
    selectedDays: List<DayOfWeek>,
    timePickerState: TimePickerState
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
                DayOfWeek.entries.forEach { day ->
                    OutlinedIconToggleButton(
                        onCheckedChange = { toggleDaySelection(day) },
                        checked = selectedDays.contains(day)
                    ) {
                        Text(day.abbreviation)
                    }
                }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TimePicker(
            state = timePickerState
        )
    }

}