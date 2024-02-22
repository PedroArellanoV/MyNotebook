package com.example.mynotebook.presentation.notes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotebook.presentation.notes.composables.NotesItem

@Composable
fun NotesScreen(
    modifier: Modifier,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notesList = viewModel.notesList.value
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add Note"
                )
            }
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(notesList) { notes ->
                NotesItem(
                    title = notes.title,
                    content = notes.content
                )
            }
        }
    }
}