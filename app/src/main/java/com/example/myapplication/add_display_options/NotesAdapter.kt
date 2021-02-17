package com.example.myapplication.add_display_options

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.add_display_options.NotesAdapter.*
import com.example.myapplication.database.Entity
import com.example.myapplication.databinding.ListItemAllNotesBinding


class NotesAdapter(val clickListener: NotesListListener) : ListAdapter<Entity, NotesViewHolder>(NotesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val current = getItem(position)!!
        holder.bind(current, clickListener)
    }

    class NotesViewHolder private constructor(val binding: ListItemAllNotesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(text: Entity, clickListener: NotesListListener) {
            binding.entity = text
            binding.dateTextView.text = text.currentDateTime
            binding.titleTextView.text = text.title
            binding.descriptionTextView.text = text.description
            binding.clickListener = clickListener
            binding.expandImage.setOnClickListener {
                binding.cardViewToExpand.visibility = View.VISIBLE
                binding.expandImage.visibility = View.GONE
            }
            binding.collapseButton.setOnClickListener {
                binding.cardViewToExpand.visibility = View.GONE
                binding.expandImage.visibility  = View.VISIBLE
            }

        }


        companion object {
            fun create(parent: ViewGroup): NotesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAllNotesBinding.inflate(
                        layoutInflater, parent, false)
                return NotesViewHolder(binding)
            }
        }
    }

    class NotesComparator : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean {
            return oldItem == newItem
        }

        @Nullable
        override fun getChangePayload(oldItemPosition: Entity, newItemPosition: Entity): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

}
interface NotesListListener{
    fun onClick(entity: Entity)
    fun onClickDelete(entity: Entity)
}
