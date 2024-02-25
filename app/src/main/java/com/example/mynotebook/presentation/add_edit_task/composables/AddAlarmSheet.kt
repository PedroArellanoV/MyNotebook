package com.example.mynotebook.presentation.add_edit_task.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotebook.presentation.add_edit_task.utils.DayOfWeek
import com.example.mynotebook.presentation.add_edit_task.utils.Options

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmSheet(
    isAlarmActive: Boolean,
    onAlarmStateSwitch: (Boolean) -> Unit,
    options: List<Options> = listOf(Options.DAILY, Options.CALENDAR),
    selectedType: Options = Options.DAILY,
    onTypeChange: (Int) -> Unit,
    toggleDaySelection: (DayOfWeek) -> Unit,
    selectedDays: List<DayOfWeek>,
    timePickerState: TimePickerState,
    onSaveAlarm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var isActive = remember { mutableStateOf(isAlarmActive) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Activate")
            Spacer(modifier = Modifier.width(4.dp))
            Switch(
                checked = isActive.value,
                onCheckedChange = {
                    isActive.value = !isActive.value
                    onAlarmStateSwitch(isActive.value)
                }
            )
        }
        HorizontalDivider(modifier = Modifier.padding(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SingleChoiceSegmentedButtonRow {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        selected = index == selectedType.index,
                        onClick = {
                            onTypeChange(index)
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        )
                    ) {
                        Text(text = label.name)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        when (selectedType) {
            Options.DAILY -> {
                DailySection(
                    toggleDaySelection = { day -> toggleDaySelection(day) },
                    selectedDays = selectedDays,
                    timePickerState = timePickerState
                )
            }

            /*
            Options.CALENDAR -> {
            Column {
                    DatePicker(state = datePickerState)
                }
            }
             */

            else -> {}
        }
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { onDismissRequest() }) {
                Text(
                    text = "Cancel"
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = { onSaveAlarm() }) {
                Text(
                    text = "Add",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

