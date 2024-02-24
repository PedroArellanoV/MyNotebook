package com.example.mynotebook.data.di

import android.app.Application
import androidx.room.Room
import com.example.mynotebook.data.data_source.NoteDatabase
import com.example.mynotebook.data.data_source.TaskDatabase
import com.example.mynotebook.data.repository.NoteRepositoryImpl
import com.example.mynotebook.data.repository.TaskRepositoryImpl
import com.example.mynotebook.domain.notes.repository.NoteRepository
import com.example.mynotebook.domain.notes.use_cases.AddNote
import com.example.mynotebook.domain.notes.use_cases.DeleteNote
import com.example.mynotebook.domain.notes.use_cases.GetNote
import com.example.mynotebook.domain.notes.use_cases.GetNotes
import com.example.mynotebook.domain.notes.use_cases.NoteUseCases
import com.example.mynotebook.domain.task.repository.TaskRepository
import com.example.mynotebook.domain.task.use_cases.AddTask
import com.example.mynotebook.domain.task.use_cases.DeleteTask
import com.example.mynotebook.domain.task.use_cases.GetTaskById
import com.example.mynotebook.domain.task.use_cases.GetTasks
import com.example.mynotebook.domain.task.use_cases.TaskUseCases
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
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.NOTE_DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            getNote = GetNote(repository),
            addNote = AddNote(repository),
            deleteNote = DeleteNote(repository)
        )
    }


    @Provides
    fun providesTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.TASK_DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun providesTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getTasks = GetTasks(repository),
            getTaskById = GetTaskById(repository),
            addTask = AddTask(repository),
            deleteTask = DeleteTask(repository)
        )
    }
}