package com.example.mynotebook.presentation.tasks.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotebook.ui.theme.Typography

@Composable
fun TaskItem(
    title: String,
    state: Boolean,
    id: Int,
    modifier: Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(60.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(
                text = title,
                style = Typography.titleMedium,
                maxLines = 1
            )
            Checkbox(
                checked = state,
                onCheckedChange = {}
            )
        }
    }
}