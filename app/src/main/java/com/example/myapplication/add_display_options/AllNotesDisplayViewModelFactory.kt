package com.example.myapplication.add_display_options

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.NotesDao

class AllNotesDisplayViewModelFactory(private val databaseDao: NotesDao,
                                      private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllNotesDisplayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllNotesDisplayViewModel(databaseDao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}