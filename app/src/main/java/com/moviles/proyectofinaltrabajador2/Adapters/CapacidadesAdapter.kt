package com.moviles.proyectofinaltrabajador2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Items.Capacidad
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Categories

class CapacidadesAdapter(val data: ArrayList<Categories>, val listener: onCapacidadClickListener) :
    RecyclerView.Adapter<CapacidadesAdapter.CapacidadesViewHolder>() {




    interface onCapacidadClickListener {
        fun onCapacidadClickListener(capacidad: Categories)

    }

    class CapacidadesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo = itemView.findViewById<TextView>(R.id.txtTitulo)
        val txtDescripcion = itemView.findViewById<TextView>(R.id.txtDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapacidadesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_capacidad, parent, false)
        return CapacidadesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CapacidadesViewHolder, position: Int) {
        val capacidad = data[position]
        holder.txtTitulo.text = capacidad.category?.name
        holder.txtDescripcion.text = capacidad.description
        holder.itemView.setOnClickListener {
            listener.onCapacidadClickListener(capacidad)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

