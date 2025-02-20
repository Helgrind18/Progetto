package com.example.progetto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.Entity.Schemi.Pullman

class CorseAdapter(): ListAdapter<Pullman, CorseAdapter.CorseViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CorseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_corsa, parent, false)
        return CorseViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: CorseViewHolder, position: Int) {
        val corsa = getItem(position)
        holder.bind(corsa)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pullman>() {
            override fun areItemsTheSame(oldItem: Pullman, newItem: Pullman): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Pullman, newItem: Pullman): Boolean =
                oldItem == newItem
        }
    }

    class CorseViewHolder(itemView: View) : ViewHolder(itemView) {
        private val idCorsa: TextView = itemView.findViewById(R.id.idCorsa)
        private val destinazione: TextView = itemView.findViewById(R.id.destinazione)
        private val partenza: TextView = itemView.findViewById(R.id.partenza)

        fun bind(corsa: Pullman) {
            idCorsa.text = corsa.id.toString()
            destinazione.text = corsa.destinazione

            val orarioPartenza = corsa.orarioPartenza.toString().padStart(4, '0') // Garantisce almeno 4 cifre
            //effetuiamo il parsing della stringa per separare ore e minuti
            if (orarioPartenza.length >= 4) {
                val ore = orarioPartenza.substring(0, 2)
                val minuti = orarioPartenza.substring(2, 4)
                partenza.text = "$ore:$minuti"
            } else {
                partenza.text = "??:??"  // Valore di default in caso di errore
            }
        }

    }
}
