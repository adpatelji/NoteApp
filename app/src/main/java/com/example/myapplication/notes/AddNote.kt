package com.example.myapplication.notes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.add_display_options.AllNotesDisplayViewModel
import com.example.myapplication.add_display_options.AllNotesDisplayViewModelFactory
import com.example.myapplication.database.NotesDatabase
import com.example.myapplication.databinding.AddNoteFragmentBinding
import com.example.myapplication.databinding.AllNotesDisplayFragmentBinding

class AddNote : Fragment() {

    companion object {
        fun newInstance() = AddNote()
    }

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = AddNoteFragmentBinding.inflate(layoutInflater,container,false)

        val application = requireNotNull(this.activity).application
        val databaseDao = NotesDatabase.getInstance(application).notesDao

        val factory = AddNoteViewModelFactory( databaseDao,application)
        val addNoteViewModel = ViewModelProvider(this , factory).get(AddNoteViewModel::class.java)
        binding.viewModel = addNoteViewModel
        binding.lifecycleOwner = this

        addNoteViewModel.navigateBack.observe(viewLifecycleOwner , {
            if (it){
                findNavController().navigate(AddNoteDirections.actionAddNoteToAllNotesDisplay())
            }
        })

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}