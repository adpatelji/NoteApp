package com.example.myapplication.add_display_options

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.R
import com.example.myapplication.database.Entity
import com.example.myapplication.database.NotesDatabase
import com.example.myapplication.databinding.AllNotesDisplayFragmentBinding
import com.google.android.material.snackbar.Snackbar


class AllNotesDisplay : Fragment() {

    private lateinit var notesViewModel1:AllNotesDisplayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = AllNotesDisplayFragmentBinding.inflate(layoutInflater, container, false)

        val application = requireNotNull(this.activity).application
        val databaseDao = NotesDatabase.getInstance(application).notesDao

        val factory = AllNotesDisplayViewModelFactory(databaseDao, application)
        val notesViewModel = ViewModelProvider(this, factory).get(AllNotesDisplayViewModel::class.java)
        notesViewModel1 = notesViewModel


        val adapter = NotesAdapter(object : NotesListListener{
            override fun onClick(entity: Entity) {
                notesViewModel.onNoteClicked(entity.id)
            }

            override fun onClickDelete(entity: Entity) {
                notesViewModel.deleteNote(entity.id)
            }

        })



        binding.sleepList.adapter = adapter
        binding.sleepList.layoutManager = LinearLayoutManager(application)
        binding.viewModel = notesViewModel
        binding.lifecycleOwner = this

        notesViewModel.notes.observe(this.viewLifecycleOwner, {
            if (it != null) {
                adapter.submitList(it)
            }
        })



        notesViewModel.navigateToNotesDescription.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(
                    AllNotesDisplayDirections.actionAllNotesDisplayToNotesDescription(
                        it
                    )
                )
                notesViewModel.onNoteDescriptionNavigated()
            }

        })

        notesViewModel.navigateToNotesAddition.observe(this.viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(AllNotesDisplayDirections.actionAllNotesDisplayToAddNote())
                notesViewModel.doneNavigating()

            }
        })

        notesViewModel.showSnackBarEvent.observe(this.viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "Notes Deleted Successfully",
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                notesViewModel.doneShowingSnackbar()
            }
        })

        setHasOptionsMenu(true)


        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear_all_menu_item -> {
                notesViewModel1.clearNotes()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
