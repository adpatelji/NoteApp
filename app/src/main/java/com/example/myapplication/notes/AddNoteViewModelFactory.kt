package com.example.myapplication.notes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.NotesDao


class AddNoteViewModelFactory(private val databaseDao: NotesDao,
                              private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddNoteViewModel(databaseDao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}