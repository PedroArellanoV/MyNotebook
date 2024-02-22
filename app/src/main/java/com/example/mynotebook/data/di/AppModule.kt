package com.example.mynotebook.data.di

import com.example.mynotebook.data.data_source.NoteDatabase
import com.example.mynotebook.data.repository.NoteRepositoryImpl
import com.example.mynotebook.domain.notes.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }
}