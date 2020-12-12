package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: Entity)

    @Query("select * from my_notes ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Entity>>

    @Query("SELECT * from my_notes WHERE id = :key")
    suspend fun get(key: Long): Entity?

    @Query("DELETE FROM my_notes")
    fun clear()

    @Query("SELECT * FROM my_notes ORDER BY id DESC LIMIT 1")
    fun getCurrentNote(): Entity?

    @Query("SELECT * from my_notes WHERE id = :key")
    fun getNightWithId(key: Long): LiveData<Entity>

    @Query("delete from my_notes where id =:key")
    fun deleteNote(key: Long)
}