package com.example.progetto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso

class LezioneAdapter() : ListAdapter<RelazioneStudenteCorso, LezioneAdapter.LezioneViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LezioneViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_lezione,parent,false)
        return LezioneViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LezioneViewHolder, position: Int) {
        val lezione=getItem(position)
        holder.bind(lezione)

    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RelazioneStudenteCorso>() {
            override fun areItemsTheSame(oldItem: RelazioneStudenteCorso, newItem: RelazioneStudenteCorso): Boolean= oldItem.corsoId==newItem.corsoId
            override fun areContentsTheSame(oldItem: RelazioneStudenteCorso, newItem: RelazioneStudenteCorso): Boolean=oldItem==newItem
        }

    }

    class LezioneViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.TestoNomeLezione)
        private val aula: TextView = itemView.findViewById(R.id.TestoAulaLezione)
        private val ora: TextView = itemView.findViewById(R.id.TestoInizioLezione)

        fun bind(lezione: RelazioneStudenteCorso) {
            titleTextView.text=lezione.nomeCorso
            aula.text=lezione.aula
            ora.text=lezione.ora
        }

    }
}