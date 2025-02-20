package com.example.progetto.Esami

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.ActivityRiutilizzabile
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.R

class CorsoAdapter(var context: Context): ListAdapter<Corso, CorsoAdapter.CorsoViewHolder>(DIFF_CALLBACK)  {

    override fun onBindViewHolder(holder: CorsoViewHolder, position: Int) {
        val corso = getItem(position)
        holder.bind(corso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorsoViewHolder {
        val context: Context = context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_corso, parent, false)
        return CorsoViewHolder(itemView, context)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Corso>() {
            override fun areItemsTheSame(oldItem: Corso, newItem: Corso): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Corso, newItem: Corso): Boolean =
                oldItem == newItem
        }
    }

    class CorsoViewHolder(itemView: View, context: Context) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.infoCorso)
        private val context: Context = context

        fun bind(corso: Corso) {
            titleTextView.text = buildString {
                append(corso.nome)
                append(", CFU: ")
                append(corso.CFU)
            }
            // tramite l'OnClickListener posso portare l'utente su una nuova pagina con le informazioni del corso
            titleTextView.setOnClickListener {
                val intent = Intent(context, ActivityRiutilizzabile::class.java).apply {
                    putExtra("id", corso.id)
                    putExtra("nome", corso.nome)
                }
                context.startActivity(intent)
            }
        }
    }
}