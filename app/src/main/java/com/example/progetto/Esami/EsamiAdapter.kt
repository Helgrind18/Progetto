package com.example.progetto.Esami

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.Entity.RelazioneStudenteCorso
import com.example.progetto.R
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EsamiAdapter() : ListAdapter<RelazioneStudenteCorso, EsamiAdapter.EsamiViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: EsamiViewHolder, position: Int) {
        val esame = getItem(position)
        holder.bind(esame)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EsamiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_esame, parent, false)
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
        private val titleTextView: TextView = itemView.findViewById(R.id.infoEsame)
        private val bottonePrestiti: Button = itemView.findViewById(R.id.bottone)
        private val barra: View = itemView.findViewById(R.id.barra)

        fun bind(esame: RelazioneStudenteCorso) {
            titleTextView.text = buildString {
                append(esame.corsoId)
                append(", ")
                append(esame.giorno)
                append(", ")
                append(esame.ora)
                append(esame.aula)
            }
            bottonePrestiti.setOnClickListener {
                gestisciClickPrestiti(titleTextView, bottonePrestiti, barra, esame)
            }
        }
    }
}

private fun gestisciClickPrestiti(tview: TextView, button: Button, barra: View, esame: RelazioneStudenteCorso) {
    esame.prenotazione=1
    Handler(Looper.getMainLooper()).postDelayed({
        tview.visibility = TextView.GONE
        button.visibility = Button.GONE
        barra.visibility = View.GONE
    }, 200)

}

