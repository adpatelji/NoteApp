package com.example.myapplication.notes

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.myapplication.database.NotesDatabase
import com.example.myapplication.databinding.AddNoteFragmentBinding
import com.example.myapplication.validate
import java.io.IOException

class AddNote : Fragment() {

    companion object {
        fun newInstance() = AddNote()
    }

    private lateinit var viewModel: AddNoteViewModel
    private var imageBitmap:Bitmap? = null
    private lateinit var binding: AddNoteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddNoteFragmentBinding.inflate(layoutInflater,container,false)

        val application = requireNotNull(this.activity).application
        val databaseDao = NotesDatabase.getInstance(application).notesDao

        val factory = AddNoteViewModelFactory( databaseDao,application)
        val addNoteViewModel = ViewModelProvider(this , factory).get(AddNoteViewModel::class.java)
        binding.viewModel = addNoteViewModel
        binding.lifecycleOwner = this

        addNoteViewModel.navigateBack.observe(viewLifecycleOwner , {
            if (it){
                findNavController().navigate(AddNoteDirections.actionAddNoteToAllNotesDisplay())
                addNoteViewModel.doneNavigating()
            }
        })


        binding.fromGalleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, 123)
        }
        binding.captureButton.setOnClickListener {
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(captureIntent, 456)
        }
        binding.titleTextView.validate("It shouldn't be empty") {
            binding.saveButton.isEnabled = it.isNotEmpty()

            it.isNotEmpty();
        }
        binding.descriptionEditText.validate("It shouldn't be empty") {
            it.isNotEmpty();
        }




        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }


    private fun loadFromUri(photoUri: Uri?): Bitmap? {
        var image: Bitmap? = null
        try {
            val activity= this.activity
            if(activity!=null) {
                val source: ImageDecoder.Source =
                        ImageDecoder.createSource(activity.contentResolver, photoUri!!)
                image = ImageDecoder.decodeBitmap(source)
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 123) {
            val imageUri = data?.data!!
            val selectedImage = loadFromUri(imageUri)
            imageBitmap  = selectedImage

        }
        else if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 456){
            val imageBitmap1 = data?.extras?.get("data") as Bitmap
            imageBitmap = imageBitmap1
        }
        viewModel.bitmap = imageBitmap
        binding.imageView.setImageBitmap(imageBitmap)


    }


}
