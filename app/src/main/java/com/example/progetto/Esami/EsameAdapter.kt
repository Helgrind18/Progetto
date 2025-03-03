package com.example.progetto.Esami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.R

class EsameAdapter(): ListAdapter<RelazioneStudenteCorso, EsameAdapter.EsamiViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: EsamiViewHolder, position: Int) {
        val esame = getItem(position)
        holder.bind(esame)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EsamiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.esame_libretto, parent, false)
        return EsamiViewHolder(itemView)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RelazioneStudenteCorso>() {
            override fun areItemsTheSame(oldItem: RelazioneStudenteCorso, newItem: RelazioneStudenteCorso): Boolean =
                oldItem.corsoId == newItem.corsoId

            override fun areContentsTheSame(oldItem: RelazioneStudenteCorso, newItem: RelazioneStudenteCorso): Boolean =
                oldItem == newItem
        }
    }

    class EsamiViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.nome)
        private val voto: TextView= itemView.findViewById(R.id.voto)
        private val passato: View= itemView.findViewById(R.id.passato)
        fun bind(esame: RelazioneStudenteCorso) {
            titleTextView.text = esame.nomeCorso.toString()
            // se l'esame è stato superato, colorerò in verde un elemento di layout per segnalarlo, altrmenti sarà di colore rosso
            if (esame.voto!=-1){
                voto.text=esame.voto.toString()
                passato.background=itemView.context.getDrawable(R.color.verde_chiaro)
            }else{
                voto.text="N/D"
                passato.background=itemView.context.getDrawable(R.color.rosso)
            }

        }
    }
}