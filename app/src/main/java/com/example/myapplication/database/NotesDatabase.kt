package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Entity::class] , version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotesDatabase:RoomDatabase() {
    abstract val notesDao:NotesDao

    companion object{

        @Volatile
        private var INSTANCE:NotesDatabase? = null

        fun getInstance(context: Context):NotesDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}