package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.moviles.proyectofinaltrabajador2.Adapters.CapacidadesAdapter
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Categories
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository

class ActivityVerCapacidades : AppCompatActivity(), WorkerRepository.onCapacidadListener,
    CapacidadesAdapter.onCapacidadClickListener {

    private lateinit var recyclerCapacidades: RecyclerView
    private lateinit var btnFloating : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_capacidades)
        setUpListView()
        setUpCallFromApi()
    }

    private fun setUpCallFromApi() {
        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
        if (token != null) {
            WorkerRepository.getWorkerCompleto(1, this, "Bearer $token")
        }
    }

    private fun setUpListView() {
        recyclerCapacidades = findViewById(R.id.recyclerCapacidades)
        btnFloating = findViewById(R.id.floatingActionButton)
        btnFloating.setOnClickListener {
            val intent = Intent(this, com.moviles.proyectofinaltrabajador2.EditarCapacidad.ActivityEditarCapacidades::class.java)
            startActivity(intent)
        }
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
        val intent  = Intent(this, com.moviles.proyectofinaltrabajador2.EditarCapacidad.ActivityEditarCapacidades::class.java)
        intent.putExtra("nombre", capacidad.category?.name)
        intent.putExtra("descripcion", capacidad.description)
        intent.putExtra("id", capacidad.category?.id)
        startActivity(intent)
    }


}