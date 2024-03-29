package com.example.mynotebook.presentation.add_edit_task

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotebook.R
import com.example.mynotebook.presentation.add_edit_task.composables.AddAlarmSheet
import com.example.mynotebook.ui.theme.Typography
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import kotlinx.coroutines.flow.collectLatest




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController, viewModel: AddEditTaskViewModel = hiltViewModel(),
    context: Context
) {

    val taskTitle = viewModel.taskTitle.value
    val taskDescription = viewModel.taskDescription.value
    val alarmState = viewModel.alarmState.value
    val typeOfAlarm = viewModel.typeOfAlarm.value
    val selectedDays = viewModel.selectedDays.value

    val alarmPermission = viewModel.alarmPermission.value

    val timePickerState =
        rememberTimePickerState(viewModel.selectedHour.value, viewModel.selectedMinute.value, false)
    val datePickerState: DatePickerState =
        rememberDatePickerState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditTaskViewModel.TaskUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditTaskViewModel.TaskUiEvent.SaveTask -> {
                    navController.navigateUp()
                }
            }
        }
    }

    LaunchedEffect(key1 = true){
        if(viewModel.alarmPermission.value){
            context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onSaveTask()
        }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
        }
    }, topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = Typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back"
                )
            }
        })
    }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            OutlinedTextField(value = taskTitle,
                onValueChange = { title ->
                    viewModel.onTitleChanged(title)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text(text = "Task name") },
                maxLines = 1
            )
            OutlinedTextField(value = taskDescription,
                onValueChange = { description ->
                    viewModel.onDescriptionChanged(description)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                label = { Text(text = "Description") },
                minLines = 5,
                maxLines = 5
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ), onClick = {
                    viewModel.onDeleteTask()
                    navController.navigateUp()
                }) {
                    Row(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = "Delete task")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            modifier = Modifier.sizeIn(maxWidth = 30.dp, maxHeight = 30.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }

                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilterChip(onClick = { showBottomSheet = true },
                        label = { Text("Alarm") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Alarm,
                                contentDescription = "Add alarm",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        },
                        enabled = taskTitle.isNotEmpty(),
                        selected = alarmState.isActive
                    )
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
                    AddAlarmSheet(isAlarmActive = alarmState.isActive,
                        onAlarmStateSwitch = { active -> viewModel.toggledAlarmSwitch(active) },
                        selectedType = typeOfAlarm,
                        onTypeChange = { state -> viewModel.onAlarmTypeChange(state) },
                        toggleDaySelection = { day -> viewModel.toggleDaySelection(day) },
                        selectedDays = selectedDays,
                        timePickerState = timePickerState,
                        datePickerState = datePickerState,
                        onSaveAlarm = {
                            if (typeOfAlarm.index == 0) {
                                viewModel.onDailyAlarmSave(timePickerState)
                            } else if (typeOfAlarm.index == 1){
                                viewModel.onCalendarAlarmSave(datePickerState, context)
                            }
                            showBottomSheet = false
                        },
                        onDismissRequest = { showBottomSheet = false })
                }
            }

        }
    }
}