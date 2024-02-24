package com.example.mynotebook.presentation.notes.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotebook.ui.theme.Typography

@Composable
fun NotesItem(
    title: String,
    content: String,
    isFav: Boolean,
    onFavChange: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .height(180.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = title,
                maxLines = 1,
                style = Typography.titleSmall
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add some spacing
            Text(
                text = content,
                maxLines = 6,
                style = Typography.bodyMedium,
            )
            Spacer(modifier = Modifier.weight(1f)) // Occupy remaining space
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconToggleButton(
                    checked = isFav,
                    onCheckedChange = { onFavChange() },
                    modifier = Modifier.sizeIn(maxWidth = 30.dp, maxHeight = 30.dp)
                ) {
                    if (isFav) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Is Favourite",
                            modifier = Modifier.sizeIn(maxWidth = 20.dp, maxHeight = 20.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Is favourite",
                            modifier = Modifier.sizeIn(maxWidth = 20.dp, maxHeight = 20.dp)
                        )
                    }
                }
            }
        }
    }
}