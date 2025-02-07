package com.example.progetto.Esami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.progetto.Entity.Schemi.CorsoDiLaurea
import com.example.progetto.R

class CDLAdapter(): ListAdapter<CorsoDiLaurea, CDLAdapter.CDLViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CDLAdapter.CDLViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cdl,parent,false)
        return CDLViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: CDLAdapter.CDLViewHolder, position: Int) {
        val corsoDiLaurea = getItem(position)
        holder.bind(corsoDiLaurea)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CorsoDiLaurea>() {
            override fun areItemsTheSame(oldItem: CorsoDiLaurea, newItem: CorsoDiLaurea): Boolean =
                oldItem.cdlId == newItem.cdlId

            override fun areContentsTheSame(
                oldItem: CorsoDiLaurea,
                newItem: CorsoDiLaurea
            ): Boolean = oldItem == newItem
        }
    }

    class CDLViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.nomeCDL)
        private val scelta: TextView= itemView.findViewById(R.id.cdlScelto)
        private val listaScelta: RecyclerView= itemView.findViewById(R.id.listaCDL)
        private val textScelta: TextView= itemView.findViewById(R.id.scegliCDL)

        fun bind(corsoDiLaurea: CorsoDiLaurea) {
            titleTextView.text = corsoDiLaurea.nomeCDL
            titleTextView.setOnClickListener{
                scelta.text= corsoDiLaurea.nomeCDL
                listaScelta.visibility= View.GONE
                scelta.visibility= View.VISIBLE
                textScelta.visibility= View.GONE
            }
        }

    }

}