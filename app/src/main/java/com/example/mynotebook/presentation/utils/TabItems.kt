package com.example.mynotebook.presentation.utils

import androidx.annotation.StringRes
import com.example.mynotebook.R

sealed class TabItem(
    @StringRes val title: Int
){
    data object Notes: TabItem(R.string.notes_item)
    data object Tasks: TabItem(R.string.task_item)
    data object Calendar: TabItem(R.string.calendar_item)
}