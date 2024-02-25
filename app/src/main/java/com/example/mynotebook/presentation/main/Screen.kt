package com.example.mynotebook.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotebook.R
import com.example.mynotebook.presentation.notes.NotesScreen
import com.example.mynotebook.presentation.tasks.TaskScreen
import com.example.mynotebook.presentation.utils.Screens
import com.example.mynotebook.presentation.utils.TabItem.Calendar
import com.example.mynotebook.presentation.utils.TabItem.Notes
import com.example.mynotebook.presentation.utils.TabItem.Tasks

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController
) {

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabItems = listOf(Notes, Tasks)
    var buttonContent by remember {
        mutableStateOf<ButtonState>(
            ButtonState.NotesScreen(
                navigateToNotes = { navController.navigate(Screens.AddEditNotesScreen.route) }
            )
        )
    }
    val pagerState = rememberPagerState { tabItems.size }

    LaunchedEffect(selectedTabIndex) { pagerState.animateScrollToPage(selectedTabIndex) }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { buttonContent.onClickEvent() }) {
                Icon(
                    imageVector = buttonContent.imageVector,
                    contentDescription = buttonContent.contentDescription
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, item ->
                    val isSelected = pagerState.currentPage == index
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = stringResource(id = item.title),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> {
                        NotesScreen(
                            navController = navController
                        )
                        buttonContent = ButtonState.NotesScreen(navigateToNotes = {
                            navController.navigate(Screens.AddEditNotesScreen.route)
                        })
                    }

                    1 -> {
                        TaskScreen(
                            navController = navController
                        )
                        buttonContent = ButtonState.TaskScreen(navigateToTask = {
                            navController.navigate(Screens.AddEditTaskScreen.route)
                        })
                    }
                }

            }
        }
    }
}