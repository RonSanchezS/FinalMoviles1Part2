package com.moviles.proyectofinaltrabajador2.Adapters

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Items.Capacidad
import com.moviles.proyectofinaltrabajador2.models.Categories

class CapacidadesAdapter(val data : ArrayList<Categories>, val listener : onCapacidadClickListener) :
    RecyclerView.Adapter<CapacidadesAdapter.CapacidadesViewHolder>() {


    interface onCapacidadClickListener {

    }

    class CapacidadesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapacidadesViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CapacidadesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}

