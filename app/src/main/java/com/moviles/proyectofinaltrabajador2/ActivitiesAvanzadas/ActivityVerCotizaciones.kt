package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Adapters.CotizacionesAdapter
import com.moviles.proyectofinaltrabajador2.ConversacionActivity
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository

class ActivityVerCotizaciones : AppCompatActivity(), WorkerRepository.onCotizacionListener,
    CotizacionesAdapter.OnCotizacionClickListener {

    private lateinit var recyclerCotizaciones: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_cotizaciones)
        setUpListView()
        getCallFromApi()
    }

    private fun getCallFromApi() {
        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
        println("Token en cotizaciones: $token")
        WorkerRepository.getCotizaciones(this, "Bearer $token")
    }

    private fun setUpListView() {
        recyclerCotizaciones = findViewById(R.id.RecyclerCotizaciones)
    }

    override fun onFailure(t: Throwable) {
//
//
        println("Error al obtener las cotizaciones")
        println(t)
    }

    override fun onSuccess(cotizaciones: List<CotizacionConpleta>?) {
        println("Cotizaciones: $cotizaciones")
        val cotizaciones = cotizaciones
        val adapter = CotizacionesAdapter(cotizaciones as ArrayList<CotizacionConpleta>, this)
        recyclerCotizaciones.adapter = adapter
        recyclerCotizaciones.layoutManager = LinearLayoutManager(this)
    }

    override fun onCotizacionClick(cotizacion: CotizacionConpleta) {
       Toast.makeText(this, "Cotizacion: ${cotizacion.id}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ConversacionActivity::class.java)
        intent.putExtra("idCotizacion", cotizacion.id)
        startActivity(intent)
    }

    override fun onSendCostClick(cotizacion: CotizacionConpleta) {
        Toast.makeText(this, "Enviando cotizacion numero ${cotizacion.id}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ActivityCostoCotizacion::class.java)
        intent.putExtra("COTIZACIONID", cotizacion.id)
        startActivity(intent)
    }
}


