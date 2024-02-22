package com.example.mynotebook.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotebook.domain.notes.model.NoteModel

@Database(
    entities = [NoteModel::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object{
        const val NOTE_DATABASE_NAME = "notes_db"
    }
}