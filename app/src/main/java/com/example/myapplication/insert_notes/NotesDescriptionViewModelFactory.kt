package com.example.myapplication.insert_notes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.NotesDao


class NotesDescriptionViewModelFactory(
    private val notesKey: Long,
    private val dataSource: NotesDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesDescriptionViewModel::class.java)) {
            return NotesDescriptionViewModel(notesKey, dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}