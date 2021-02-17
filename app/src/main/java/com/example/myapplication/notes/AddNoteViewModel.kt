package com.example.myapplication.notes

import android.app.Application
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.Entity
import com.example.myapplication.database.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNoteViewModel(private val databaseDao: NotesDao,
                       private val application: Application) : ViewModel() {
    private val _newNotes = MutableLiveData<Entity>()
    val newNotes: LiveData<Entity>
        get() = _newNotes

    var bitmap:Bitmap?  = null

    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    init {
        _newNotes.value = Entity()
    }



    fun saveNote(){
        viewModelScope.launch (Dispatchers.Main){
            _newNotes.value?.photo = bitmap
            withContext(Dispatchers.IO){
                _newNotes.value?.let { databaseDao.insert(it) }
            }
            Toast.makeText(application,"Note Added Successfully",Toast.LENGTH_SHORT).show()
        }
        _navigateBack.value = true

    }
    fun goBack(){
        _navigateBack.value = true
    }
    fun doneNavigating() {
        _navigateBack.value = false
    }




}