package com.moviles.proyectofinaltrabajador2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Charla

class CharlaAdapter (val data  : ArrayList<Charla>)
    : RecyclerView.Adapter<CharlaAdapter.CharlaViewHolder>() {
    class CharlaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.nombreUsuario)
        val mensaje = itemView.findViewById<TextView>(R.id.mensaje)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharlaAdapter.CharlaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_charla, parent, false)
        return CharlaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharlaAdapter.CharlaViewHolder, position: Int) {
        val itemCharla = data[position]
        holder.nombre.text = itemCharla.userId.toString()
        holder.mensaje.text = itemCharla.message
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
