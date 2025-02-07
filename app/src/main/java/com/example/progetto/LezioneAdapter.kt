package com.example.progetto

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.R

class LezioneAdapter() : ListAdapter<RelazioneStudenteCorso, LezioneAdapter.LezionwViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LezioneAdapter.LezionwViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_lezione,parent,false)
        return LezionwViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LezioneAdapter.LezionwViewHolder, position: Int) {
        val lezione=getItem(position)
        holder.bind(lezione)

    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RelazioneStudenteCorso>() {
            override fun areItemsTheSame(oldItem: RelazioneStudenteCorso, newItem: RelazioneStudenteCorso): Boolean= oldItem.corsoId==newItem.corsoId
            override fun areContentsTheSame(oldItem: RelazioneStudenteCorso, newItem: RelazioneStudenteCorso): Boolean=oldItem==newItem
        }

    }

    class LezionwViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.TestoNomeLezione)
        private val aula: TextView = itemView.findViewById(R.id.TestoAulaLezione)
        private val semestre: TextView = itemView.findViewById(R.id.TestoInizioLezione)

        fun bind(lezione: RelazioneStudenteCorso) {
            titleTextView.text=lezione.nomeCorso
            aula.text=lezione.aula
            semestre.text=lezione.ora
        }

    }
}