package com.example.progetto.AreeBiblioteca

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.progetto.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.progetto.Entity.Schemi.Libro
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

// classe adapter da usare quando devono essere visualizzate liste di libri
class LibroAdapter(var context: Context, var matricola: Int) : ListAdapter<Libro, LibroAdapter.LibroViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): LibroViewHolder {
        val context: Context = context
        val matricola: Int = matricola
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(itemView, context,matricola)
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

    class LibroViewHolder(itemView: View, context: Context, matricola: Int) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.nomeLibro)
        private val context: Context = context
        private val matricola: Int = matricola

        fun bind(libro: Libro) {
            titleTextView.text = buildString {
                append(libro.name)
                append(", ")
                append(libro.autore)
            }

            // tramite l'OnClickListener posso portare l'utente su una nuova pagina con le informazioni del libro

            titleTextView.setOnClickListener{
                val intent = Intent(context, Libro_Riutilizzabile::class.java).apply {
                    putExtra("nome", libro.name)
                    putExtra("autore", libro.autore)
                    putExtra("username", matricola)
                }
                context.startActivity(intent)
            }
        }

    }
}

