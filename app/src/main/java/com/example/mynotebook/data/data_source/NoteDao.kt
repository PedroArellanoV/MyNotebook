package com.example.mynotebook.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynotebook.domain.notes.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_model")
    fun getNotes(): Flow<List<NoteModel>>

    @Query("SELECT * FROM note_model WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)
}