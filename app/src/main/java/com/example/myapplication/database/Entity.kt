package com.example.myapplication.database

import android.graphics.Bitmap
import android.text.format.DateUtils
import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "my_notes")
data class Entity(
        @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,

        @ColumnInfo(name = "date")
    val currentDateTime:String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh.mm a")),

        var title:String = "",
        var description:String = "",

        var photo: Bitmap? = null

)
