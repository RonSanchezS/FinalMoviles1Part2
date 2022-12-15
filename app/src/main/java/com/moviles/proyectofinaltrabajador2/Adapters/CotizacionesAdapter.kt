package com.moviles.proyectofinaltrabajador2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        //add onClickListener
        holder.itemView.setOnClickListener {
            listener.onCotizacionClick(cotizacion)
        }

        if (cotizacion.priceOffer == null) {
            holder.txtPrecioCotizacion.text = "Sin cotizar"
        }else{
            holder.txtPrecioCotizacion.text = cotizacion.priceOffer.toString()
        }
        holder.lblRating.visibility = View.GONE
        holder.BtnHayMapa.visibility = View.GONE

        if(cotizacion.deliveryLatitude != null && cotizacion.deliveryLongitude != null){
            holder.BtnHayMapa.visibility = View.VISIBLE
            holder.BtnHayMapa.setOnClickListener {
                listener.onMapaClick(cotizacion)
            }
        }
        when(cotizacion.status){
            0 -> {
                holder.txtEstadoCotizacion.text = "Pendiente"
                holder.btnAccion.text = "Enviar costo"
                holder.btnAccion.setOnClickListener {
                    listener.onSendCostClick(cotizacion)
                }

            }
            1 -> {
                holder.txtEstadoCotizacion.text = "Ofertada"
                holder.btnAccion.visibility = View.GONE

            }
            2 -> {
                holder.txtEstadoCotizacion.text = "Aceptada"
                holder.btnAccion.text = "Finalizar"
                holder.btnAccion.setOnClickListener {
                    listener.onFinalizadoClick(cotizacion)
                }

            }
            -2 -> {
                holder.txtEstadoCotizacion.text = "Rechazada"
                holder.btnAccion.text = "Contraoferta"
                holder.btnAccion.setOnClickListener {
                    listener.onSendCostClick(cotizacion)
                }
                holder.btnDescartar.visibility = View.VISIBLE
                holder.btnDescartar.setOnClickListener {
                    listener.onDescartarClick(cotizacion)
                }
            }
            3 -> {
                holder.txtEstadoCotizacion.text = "Finalizada"
                holder.btnAccion.visibility = View.GONE
            }
            -3 -> {holder.txtEstadoCotizacion.text = "Descartado"
                //hide button
                holder.btnAccion.visibility = View.GONE
            }
            4 -> {
                holder.txtEstadoCotizacion.text = "Calificado"
                holder.btnAccion.visibility = View.GONE
                holder.lblRating.visibility = View.VISIBLE
                holder.lblRating.rating = cotizacion.review?.toFloat()!!
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
        val lblRating = itemView.findViewById<RatingBar>(R.id.lblRating)
        val BtnHayMapa = itemView.findViewById<ImageView>(R.id.BtnHayMapa)
    }

    interface OnCotizacionClickListener {
        fun onCotizacionClick(cotizacion: CotizacionConpleta)
         fun onSendCostClick(cotizacion: CotizacionConpleta)
        fun onFinalizadoClick(cotizacion: CotizacionConpleta)
        fun onDescartarClick(cotizacion: CotizacionConpleta)
        fun onMapaClick(cotizacion: CotizacionConpleta)
    }
}
