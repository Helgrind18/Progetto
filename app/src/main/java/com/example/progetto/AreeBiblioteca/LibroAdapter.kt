package com.example.progetto.AreeBiblioteca

import android.util.Log
import com.example.progetto.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.progetto.Entity.Libro
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class LibroAdapter : ListAdapter<Libro, LibroAdapter.LibroViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int,): LibroViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = getItem(position)
        Log.d("LibroAdapter", "Binding libro: ${libro.name}")
        holder.bind(libro)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Libro>() {
            override fun areItemsTheSame(oldItem: Libro, newItem: Libro): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Libro, newItem: Libro): Boolean =
                oldItem == newItem
        }
    }

    class LibroViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.nomeLibro)

        fun bind(libro: Libro) {
            titleTextView.text = libro.name
        }

    }
}

