package com.example.myapplication

import android.graphics.Bitmap
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
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

@BindingAdapter("addSrc")
fun ImageView.setSrc(bitmap:Bitmap?){
    setImageBitmap(bitmap)
}

fun EditText.validate(message: String, validator: (String) -> Boolean) {
    this.doAfterTextChanged {
        this.error = if (validator(it.toString())) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}