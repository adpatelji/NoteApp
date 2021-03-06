package com.example.myapplication.insert_notes


import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.*
import com.example.myapplication.database.Entity
import com.example.myapplication.database.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NotesDescriptionViewModel(
    private val notesKey: Long = 0L,
    val database: NotesDao,
    application: Application
):AndroidViewModel(application){


    private val currentNote = MediatorLiveData<Entity>()


    fun getCurrentNote() = currentNote

    init {
            currentNote.addSource(database.getNightWithId(notesKey), currentNote::setValue)
//            currentNote = database.get(notesKey)!!
    }

    private val _navigateToMain =  MutableLiveData<Boolean?>()
    val navigateToMain: LiveData<Boolean?>
        get() = _navigateToMain


    fun doneNavigating() {
        _navigateToMain.value = false
    }


    fun saveCurrentNote(){
        viewModelScope.launch(Dispatchers.IO) {
            currentNote.value?.let { database.update(it) }
        }
        Toast.makeText(getApplication() ,"Updated",Toast.LENGTH_SHORT ).show()
    }
    fun goBack(){
        _navigateToMain.value = true
    }


}