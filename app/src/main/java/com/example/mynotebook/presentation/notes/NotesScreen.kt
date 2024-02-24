package com.example.mynotebook.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.presentation.notes.composables.NotesItem
import com.example.mynotebook.presentation.utils.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {
    val notesList = viewModel.noteList.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel){
        viewModel.getNotes()
    }

    Scaffold {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8    .dp)
        ) {
            items(notesList) { note ->
                NotesItem(
                    title = note.title,
                    content = note.content,
                    isFav = note.isFav,
                    onFavChange = {
                            viewModel.onFavChange(note)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(
                                Screens.AddEditNotesScreen.route + "?noteId=${note.id}"
                            )
                        }
                )
            }
        }
    }
}