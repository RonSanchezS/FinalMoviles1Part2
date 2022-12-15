package com.moviles.proyectofinaltrabajador2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.moviles.proyectofinaltrabajador2.FullScreenImage
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Charla


class CharlaAdapter(val data: ArrayList<Charla>, val idLocal: String, val listener : onMapClickListener) :
    RecyclerView.Adapter<CharlaAdapter.CharlaViewHolder>() {
    class CharlaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.nombreUsuario)
        val mensaje = itemView.findViewById<TextView>(R.id.mensaje)
        val nombreLocal = itemView.findViewById<TextView>(R.id.nombreUsuarioLocal)
        val mensajeLocal = itemView.findViewById<TextView>(R.id.mensajeLocal)
        val mapaentrante = itemView.findViewById<MapView>(R.id.mapaEntrante)
        val mapaSaliente = itemView.findViewById<MapView>(R.id.mapaSaliente)
        val imagenEntrante = itemView.findViewById<ImageView>(R.id.imagenEntrante)
        val imagenSaliente = itemView.findViewById<ImageView>(R.id.imagenSaliente)

        val btnEnviarImagen = itemView.findViewById<ImageButton>(R.id.btnEnviarImagen)
        val btnEnviarMapa = itemView.findViewById<ImageButton>(R.id.btnEnviarUbicacion)
    }

    interface onMapClickListener {
        fun onMapClick(charla: Charla)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharlaAdapter.CharlaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_charla, parent, false)
        return CharlaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharlaAdapter.CharlaViewHolder, position: Int) {
        val itemCharla = data[position]
        holder.itemView.setOnClickListener {
            listener.onMapClick(data[position])
        }
        if (itemCharla.userId == idLocal.toInt()) {
            if (itemCharla.latitude != null && itemCharla.longitude != null) {
                holder.mapaSaliente.visibility = View.VISIBLE
                //setUpMap
                holder.mapaSaliente.onCreate(null)
                holder.mapaSaliente.onResume()
                holder.mapaSaliente.getMapAsync {
                    val latLng =
                        LatLng(itemCharla.latitude!!.toDouble(), itemCharla.longitude!!.toDouble())
                    it.addMarker(MarkerOptions().position(latLng))
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
                holder.mapaSaliente.setOnClickListener {
                    listener.onMapClick(itemCharla)
                }

            } else {
                holder.mapaSaliente.visibility = View.GONE
            }
            if (itemCharla.image != null) {
                holder.imagenSaliente.visibility = View.VISIBLE
                holder.imagenSaliente.setOnClickListener{
                    val intent = Intent(holder.itemView.context, FullScreenImage::class.java)
                    intent.putExtra("image", itemCharla.image)
                    holder.itemView.context.startActivity(intent)
                }
                Glide
                    .with(holder.itemView.context)
                    .load(itemCharla.image)
                    .centerCrop()
                    .into(holder.imagenSaliente)
            } else {
                holder.imagenSaliente.visibility = View.GONE
            }

            holder.nombreLocal.text = itemCharla.user?.name
            holder.mensajeLocal.text = itemCharla.message
            holder.mensajeLocal.visibility = View.VISIBLE
            holder.nombreLocal.visibility = View.VISIBLE
            ocultarMensajesEntrantes(holder)
        } else {
            if (itemCharla.latitude != null && itemCharla.longitude != null) {
                holder.mapaentrante.visibility = View.VISIBLE
                //setUpMap
                holder.mapaentrante.onCreate(null)
                holder.mapaentrante.onResume()
                holder.mapaentrante.getMapAsync {
                    val latLng =
                        LatLng(itemCharla.latitude!!.toDouble(), itemCharla.longitude!!.toDouble())
                    it.addMarker(MarkerOptions().position(latLng))
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
                holder.mapaentrante.setOnClickListener {
                    listener.onMapClick(itemCharla)
                }
            } else {
                holder.mapaentrante.visibility = View.GONE
            }
            if (itemCharla.image != null) {
                holder.imagenEntrante.visibility = View.VISIBLE
                holder.imagenEntrante.setOnClickListener{
                    val intent = Intent(holder.itemView.context, FullScreenImage::class.java)
                    intent.putExtra("image", itemCharla.image)
                    holder.itemView.context.startActivity(intent)
                }
                Glide
                    .with(holder.itemView.context)
                    .load(itemCharla.image)
                    .centerCrop()
                    .into(holder.imagenEntrante)
            } else {
                holder.imagenEntrante.visibility = View.GONE
            }
            holder.nombre.text = itemCharla.user?.name
            holder.mensaje.text = itemCharla.message

            holder.mensaje.visibility = View.VISIBLE
            holder.nombre.visibility = View.VISIBLE

            ocultarMensajesSalientes(holder)
        }

    }

    private fun ocultarMensajesSalientes(holder: CharlaViewHolder) {
        holder.mensajeLocal.visibility = View.GONE
        holder.nombreLocal.visibility = View.GONE
        holder.mapaSaliente.visibility = View.GONE
        holder.imagenSaliente.visibility = View.GONE
    }

    private fun ocultarMensajesEntrantes(holder: CharlaViewHolder) {
        holder.mensaje.visibility = View.GONE
        holder.nombre.visibility = View.GONE
        holder.imagenEntrante.visibility = View.GONE
        holder.mapaentrante.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
