package com.moviles.proyectofinaltrabajador2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas.ActivityVerCapacidades
import com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas.ActivityVerCotizaciones
import com.moviles.proyectofinaltrabajador2.models.Usuario
import com.moviles.proyectofinaltrabajador2.repository.LoginRepository

class HomeActivity : AppCompatActivity(), LoginRepository.onDatosLoginListener {

    private lateinit var btnCapacidades: Button
    private lateinit var btnCotizaciones: Button
    private lateinit var txtNombreUsuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpListView()
        setUpListeners()
        LoginRepository.getDatosLogin(this)
    }

    private fun setUpListeners() {
        btnCapacidades.setOnClickListener {
         val intent = Intent(this, ActivityVerCapacidades::class.java)
            startActivity(intent)
        }
        btnCotizaciones.setOnClickListener {
            val intent = Intent(this,ActivityVerCotizaciones::class.java)
            startActivity(intent)
        }
    }

    private fun setUpListView() {
        btnCapacidades = findViewById(R.id.btnVerCapacidades)
        btnCotizaciones = findViewById(R.id.btnVerCotizaciones)
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)


    }

    override fun onFailure(t: Throwable) {
        println("Error: ${t.message}")
    }

    override fun onSuccess(body: Usuario) {
        txtNombreUsuario.text = body.name

    }
}