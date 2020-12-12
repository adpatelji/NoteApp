package com.example.myapplication.insert_notes

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.database.NotesDatabase
import com.example.myapplication.databinding.NotesDescriptionFragmentBinding


class NotesDescription : Fragment() {

    private lateinit var viewModel: NotesDescriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: NotesDescriptionFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.notes_description_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val arguments = NotesDescriptionArgs.fromBundle(requireArguments())

        val dataSource = NotesDatabase.getInstance(application).notesDao

        val factory = NotesDescriptionViewModelFactory(arguments.notesId, dataSource,application)
        val notesDescriptionViewModel = ViewModelProvider(this, factory).get(
            NotesDescriptionViewModel::class.java
        )

        binding.viewModel = notesDescriptionViewModel
        binding.lifecycleOwner = this

        notesDescriptionViewModel.navigateToMain.observe(viewLifecycleOwner,{
            if(it == true){
                findNavController().navigate(NotesDescriptionDirections.actionNotesDescriptionToAllNotesDisplay())
                notesDescriptionViewModel.doneNavigating()
            }
        })



//        sleepQualityViewModel.navigateToSleepTracker.observe(this.viewLifecycleOwner,  Observer {
//            if (it == true) { // Observed state is true.
//                this.findNavController().navigate(
//                    SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
//                sleepQualityViewModel.doneNavigating()
//            }
//        })
        closeKeyBoard()

        return binding.root
    }
    private fun closeKeyBoard() {
        val imm = activity
            ?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}