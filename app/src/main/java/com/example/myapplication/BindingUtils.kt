package com.example.myapplication

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myapplication.database.Entity

@BindingAdapter("date")
fun TextView.setDate(item: Entity?) {
    item?.let {
        text =item.currentDateTime
    }
}

@BindingAdapter("title")
fun EditText.setTitle(item: Entity?) {
    item?.let {
        setText(item.title)
    }
}

@BindingAdapter("description")
fun EditText.setDescription(item: Entity?) {
    item?.let {
        setText(item.description)

    }
}
