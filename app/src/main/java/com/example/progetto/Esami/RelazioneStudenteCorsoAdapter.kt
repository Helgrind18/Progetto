package com.example.progetto.Esami

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.R
import java.util.Calendar


class RelazioneStudenteCorsoAdapter() : ListAdapter<RelazioneStudenteCorso, RelazioneStudenteCorsoAdapter.EsamiViewHolder>(DIFF_CALLBACK) {

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
            val calendar = Calendar.getInstance()
            val anno= calendar.get(Calendar.YEAR)
            val mese= calendar.get(Calendar.MONTH)+1

            titleTextView.text = buildString {
                append(esame.nomeCorso)
                append("\nGiorno: ")
                append(esame.giorno)
                append("/")
                append(mese)
                append("/")
                append(anno)
                append("\nOra: ")
                append(esame.ora)
                append("\nAula: ")
                append(esame.aula)
            }
            bottonePrestiti.setOnClickListener {
                gestisciClickPrenotazioni(titleTextView, bottonePrestiti, barra, esame)
            }
        }
    }
}


private fun gestisciClickPrenotazioni(tview: TextView, button: Button, barra: View, esame: RelazioneStudenteCorso) {
    // aggiorno il valore del campo prenotazione e, dopo aver atteso 1,5 secondi, rendo invisibile l'elemento e con un Toast segnalo il successo
    esame.prenotazione=1
    Handler(Looper.getMainLooper()).postDelayed({
        tview.visibility = TextView.GONE
        button.visibility = Button.GONE
        barra.visibility = View.GONE
        Toast.makeText(tview.context, "Esame prenotato con successo", Toast.LENGTH_SHORT).show()
    }, 1500)

}

