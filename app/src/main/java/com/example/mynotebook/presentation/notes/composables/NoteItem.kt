package com.example.mynotebook.presentation.notes.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotebook.ui.theme.Typography

@Composable
fun NotesItem(
    title: String,
    content: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp)
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = title,
                maxLines = 1,
                style = Typography.titleSmall
            )
            Text(
                text = content,
                maxLines = 6,
                style = Typography.bodyMedium
            )
        }
    }
}