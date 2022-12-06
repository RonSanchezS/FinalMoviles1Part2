package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository

class ActivityVerCotizaciones : AppCompatActivity(), WorkerRepository.onCotizacionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_cotizaciones)
        WorkerRepository.getCotizaciones(this)

    }

    override fun onFailure(t: Throwable) {
//
//
        println("Error al obtener las cotizaciones")
        println(t)
   }

    override fun onSuccess(cotizaciones: List<CotizacionConpleta>?) {
        println("Cotizaciones: $cotizaciones")
    }
}