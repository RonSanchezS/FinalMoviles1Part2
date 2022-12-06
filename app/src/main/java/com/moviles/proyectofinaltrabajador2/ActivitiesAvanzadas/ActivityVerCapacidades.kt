package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Adapters.CapacidadesAdapter
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Categories
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository

class ActivityVerCapacidades : AppCompatActivity(), WorkerRepository.onCapacidadListener,
    CapacidadesAdapter.onCapacidadClickListener {

    private lateinit var recyclerCapacidades: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_capacidades)
        setUpListView()
        WorkerRepository.getWorkerCompleto(1, this)
    }

    private fun setUpListView() {
        recyclerCapacidades = findViewById(R.id.recyclerCapacidades)
    }

    override fun onFailure(t: Throwable) {
        //
    }

    override fun onSuccess(workerCompleto: WorkerCompleto?) {
        println("workerCompleto: ${workerCompleto}")
        val cotizaciones = workerCompleto?.categories
        val adapter = CapacidadesAdapter(cotizaciones!! as ArrayList<Categories>, this)
        recyclerCapacidades.layoutManager = LinearLayoutManager(this)
        recyclerCapacidades.adapter = adapter

    }

    override fun onCapacidadClickListener(capacidad: Categories) {



    }


}