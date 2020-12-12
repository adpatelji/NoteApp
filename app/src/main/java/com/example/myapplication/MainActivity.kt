package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.database.Entity
import com.example.myapplication.database.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.myNameHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController )

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNameHostFragment)
        return navController.navigateUp() or super.onSupportNavigateUp()
    }

}