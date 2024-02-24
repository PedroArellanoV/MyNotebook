package com.example.mynotebook.presentation.add_edit_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotebook.R
import com.example.mynotebook.ui.theme.Typography
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {

    val taskTitle = viewModel.taskTitle.value
    val taskDescription = viewModel.taskDescription.value
    val taskState = viewModel.taskState.value
    val taskAlarm = viewModel.alarmState.value

    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onSaveTask()
                }
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = Typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Switch(
                        modifier = Modifier
                            .padding(8.dp),
                        checked = taskState,
                        onCheckedChange = { state ->
                            viewModel.onStateChanged(state)
                        },
                        thumbContent = {
                            if (taskState) {
                                Icon(
                                    modifier = Modifier.size(18.dp, 18.dp),
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.size(18.dp, 18.dp),
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }

                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            OutlinedTextField(
                value = taskTitle,
                onValueChange = { title ->
                    viewModel.onTitleChanged(title)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text(text = "Task name") },
                maxLines = 1
            )
            OutlinedTextField(
                value = taskDescription,
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
            if(taskAlarm.isActive){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    OutlinedIconToggleButton( checked = false, onCheckedChange = {}) {
                        Text(text = "M")
                    }
                    OutlinedIconToggleButton( checked = false, onCheckedChange = {} ) {
                        Text(text = "T")
                    }
                    OutlinedIconToggleButton( checked = false, onCheckedChange = {} ) {
                        Text(text = "W")
                    }
                    OutlinedIconToggleButton( checked = true, onCheckedChange = {} ) {
                        Text(text = "Th")
                    }
                    OutlinedIconToggleButton( checked = true, onCheckedChange = {} ) {
                        Text(text = "F")
                    }
                    OutlinedIconToggleButton( checked = false, onCheckedChange = {} ) {
                        Text(text = "S")
                    }
                    OutlinedIconToggleButton( checked = false, onCheckedChange = {} ) {
                        Text(text = "Su")
                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                FilterChip(
                    onClick = { showTimePicker = true },
                    label = { Text("Alarm") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Alarm,
                            contentDescription = "Add alarm",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    },
                    enabled = taskTitle.isNotEmpty(),
                    selected = taskAlarm.isActive
                )
            }

            if (showTimePicker) {

                AlertDialog(
                    onDismissRequest = { showTimePicker = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onSecondary,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TimePicker(state = timePickerState)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { showTimePicker = false }) {
                                Text(
                                    text = "Cancel",
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            TextButton(onClick = {
                                showTimePicker = false
                                scope.launch(Dispatchers.IO) {
                                    viewModel.addAlarmToTask(
                                        timePickerState.hour,
                                        timePickerState.minute
                                    )
                                }
                            }) {
                                Text(
                                    text = "Ok",
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }


                }
            }

        }
    }
}