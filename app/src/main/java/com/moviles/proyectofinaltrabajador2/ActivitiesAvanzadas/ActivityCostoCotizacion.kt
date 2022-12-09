package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Precio
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository

class ActivityCostoCotizacion : AppCompatActivity(), WorkerRepository.listenerCotizacion {


    private lateinit var txtCosto: TextView
    private lateinit var btnEnviar: Button
    private lateinit var btnCancelar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_costo_cotizacion)
        setUpListView()
        setUpListeners()
    }

    private fun setUpListeners() {
        btnCancelar.setOnClickListener {
            finish()
        }
        btnEnviar.setOnClickListener {
            var precio = Precio(txtCosto.text.toString())
            //get token from shared preferences
            val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
            val id = intent.extras?.get("COTIZACIONID").toString()

            println(precio)
            println("token : $token")
            println("ID : $id")

            WorkerRepository.ofertarCotizacion("Bearer $token", id, this, precio)

        }

    }

    private fun setUpListView() {
        txtCosto = findViewById(R.id.txtCosto)
        btnEnviar = findViewById(R.id.btnEnviarCosto)
        btnCancelar = findViewById(R.id.btnCancelarCosto)
    }

    override fun onFailure(t: Throwable) {
        println(t.message)
    }

    override fun onSuccess(cotizaciones: CotizacionConpleta?) {
       finish()
    }
}