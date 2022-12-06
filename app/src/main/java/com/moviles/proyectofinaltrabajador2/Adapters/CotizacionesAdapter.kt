package com.moviles.proyectofinaltrabajador2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.R

class CotizacionesAdapter(
    val data: ArrayList<CotizacionConpleta>,
    val listener: OnCotizacionClickListener
) :
    RecyclerView.Adapter<CotizacionesAdapter.CotizacionesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CotizacionesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cotizacion, parent, false)
        return CotizacionesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CotizacionesViewHolder, position: Int) {
        val cotizacion = data[position]
        holder.txtNombreCotizacion.text = cotizacion.client?.user?.name
        holder.btnDescartar.visibility = View.GONE
        if (cotizacion.priceOffer == null) {
            holder.txtPrecioCotizacion.text = "Sin cotizar"
        }else{
            holder.txtPrecioCotizacion.text = cotizacion.priceOffer.toString()
        }
        when(cotizacion.status){
            0 -> {
                holder.txtEstadoCotizacion.text = "Pendiente"
                holder.btnAccion.text = "Enviar costo"

            }
            1 -> {
                holder.txtEstadoCotizacion.text = "Ofertada"
                holder.btnAccion.visibility = View.GONE

            }
            2 -> {
                holder.txtEstadoCotizacion.text = "Aceptada"
                holder.btnAccion.visibility = View.GONE

            }
            -2 -> {
                holder.txtEstadoCotizacion.text = "Rechazada"
                holder.btnAccion.text = "Contraoferta"
                holder.btnDescartar.visibility = View.VISIBLE
            }
            3 -> {
                holder.txtEstadoCotizacion.text = "Finalizada"

            }
            -3 -> {holder.txtEstadoCotizacion.text = "Descartado"
                //hide button
                holder.btnAccion.visibility = View.GONE
            }
            4 -> {
                holder.txtEstadoCotizacion.text = "Calificado"
                holder.btnAccion.visibility = View.GONE

            }
        }
        Glide
            .with(holder.itemView.context)
            .load(cotizacion.worker?.profilePicture)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgUsuario)


    }

    class CotizacionesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreCotizacion = itemView.findViewById<TextView>(R.id.txtNombreCotizacion)
        val txtEstadoCotizacion = itemView.findViewById<TextView>(R.id.txtEstadoCotizacion)
        val txtPrecioCotizacion = itemView.findViewById<TextView>(R.id.txtPrecioCotizacion)
        val btnAccion = itemView.findViewById<TextView>(R.id.btnAccion)
        val btnDescartar = itemView.findViewById<TextView>(R.id.btnDescartar)
        val imgUsuario = itemView.findViewById<ImageView>(R.id.imgUsuario)
    }

    interface OnCotizacionClickListener {
        fun onCotizacionClick(cotizacion: CotizacionConpleta)
    }
}
