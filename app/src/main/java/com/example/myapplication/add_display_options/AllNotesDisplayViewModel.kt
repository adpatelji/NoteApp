package com.example.myapplication.add_display_options

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.Entity
import com.example.myapplication.database.NotesDao
import com.example.myapplication.repositary.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllNotesDisplayViewModel(private val databaseDao: NotesDao
                                ,application: Application) : ViewModel() {

    var repository = NoteRepository(databaseDao)
    var notes = repository.notes







    //navigate to Add note

    private val _navigateToNotesAddition = MutableLiveData<Boolean>()
    val navigateToNotesAddition:LiveData<Boolean>
        get() = _navigateToNotesAddition
    fun onAddButtonClicked(){
        _navigateToNotesAddition.value = true
    }
    fun doneNavigating() {
        _navigateToNotesAddition.value = false
    }
    //    navigate to Add note



    //navigate to Description note
    private val _navigateToNotesDescription = MutableLiveData<Long>()
    val navigateToNotesDescription
        get() = _navigateToNotesDescription
    fun onNoteClicked(id: Long){
        _navigateToNotesDescription.value = id
    }
    fun onNoteDescriptionNavigated() {
        _navigateToNotesDescription.value = null
    }
    //    navigate to description note


    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }


    fun clearNotes(){
        viewModelScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {
                databaseDao.clear()
            }
            _showSnackbarEvent.value = true
        }
    }

    fun deleteNote(key:Long){
        viewModelScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {
                databaseDao.deleteNote(key)
            }

        }
    }

    fun byDate(){
        notes = repository.refreshVideos(1)
    }
    fun hasImage(){
        notes = repository.refreshVideos(2)
    }
    fun notHasImage(){
        notes = repository.refreshVideos(3)
    }


}