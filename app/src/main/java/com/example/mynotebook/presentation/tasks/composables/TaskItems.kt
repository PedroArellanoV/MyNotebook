package com.example.mynotebook.presentation.tasks.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mynotebook.ui.theme.Typography

@Composable
fun TaskItem(
    title: String,
    state: Boolean,
    hour: String,
    modifier: Modifier,
    onCheckedChange: () -> Unit,
    onCardClicked: () -> Unit
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    onCardClicked()
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
                Row(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (state) {
                        Icon(
                            imageVector = Icons.Default.AlarmOn,
                            contentDescription = "Alarm On",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AlarmOff,
                            contentDescription = "Alarm Off",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    /*Text(
                        text = hour,
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )*/
                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )
                    Switch(
                        checked = state,
                        onCheckedChange = { onCheckedChange() },
                        thumbContent = {
                            if (state) {
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

            }
        }
    }

}