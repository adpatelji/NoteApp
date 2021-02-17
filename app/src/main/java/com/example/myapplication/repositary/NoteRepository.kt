package com.example.myapplication.repositary

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.myapplication.database.Entity
import com.example.myapplication.database.NotesDao
import com.example.myapplication.database.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private  val dao: NotesDao) {
    val notes: LiveData<List<Entity>> = dao.getAllNotes()


    fun refreshVideos(no:Int): LiveData<List<Entity>> {
        return when(no){
                2 -> dao.getAllNotesThatHasImage()
                3 -> dao.getAllNotesThatHasNotImage()
                else -> dao.getAllNotes()
            }
    }

}