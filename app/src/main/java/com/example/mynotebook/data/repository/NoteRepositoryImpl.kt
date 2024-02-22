package com.example.mynotebook.data.repository

import com.example.mynotebook.data.data_source.NoteDao
import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.domain.notes.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<NoteModel>> = dao.getNotes()

    override suspend fun getNoteById(id: Int): NoteModel? = dao.getNoteById(id)

    override suspend fun insertNote(note: NoteModel) = dao.insertNote(note)

    override suspend fun deleteNote(note: NoteModel) = dao.deleteNote(note)
}