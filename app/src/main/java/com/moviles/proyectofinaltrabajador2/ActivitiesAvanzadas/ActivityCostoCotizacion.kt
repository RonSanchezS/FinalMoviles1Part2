package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Precio
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository

class ActivityCostoCotizacion : AppCompatActivity() {


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
        //    val id
          //  WorkerRepository.ofertarCotizacion(token, id, this, precio)


        }

    }

    private fun setUpListView() {
        txtCosto = findViewById(R.id.txtCosto)
        btnEnviar = findViewById(R.id.btnEnviarCosto)
        btnCancelar = findViewById(R.id.btnCancelarCosto)
    }
}