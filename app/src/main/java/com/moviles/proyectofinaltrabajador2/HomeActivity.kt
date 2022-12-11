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
    private lateinit var btnLogout: Button
    private lateinit var txtNombreUsuario: TextView

    private lateinit var idUsuario : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpListView()
        setUpListeners()
        //get token from shared prefferences
        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
        println("token: $token")
        if (token != null) {
            LoginRepository.getDatosLogin(this, "Bearer $token")
        }
    }

    private fun setUpListeners() {
        btnCapacidades.setOnClickListener {
            val intent = Intent(this, ActivityVerCapacidades::class.java)
            intent.putExtra("idTrabajador", idUsuario)
            startActivity(intent)
        }
        btnCotizaciones.setOnClickListener {
            val intent = Intent(this,ActivityVerCotizaciones::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener {
            //
            val sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            finish()
        }
    }

    private fun setUpListView() {
        btnCapacidades = findViewById(R.id.btnVerCapacidades)
        btnCotizaciones = findViewById(R.id.btnVerCotizaciones)
        btnLogout = findViewById(R.id.btnLogout)
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)


    }

    override fun onFailure(t: Throwable) {
        println("Error: ${t.message}")
    }

    override fun onSuccess(body: Usuario) {
        txtNombreUsuario.text = body.name
        idUsuario = body.id.toString()
        val pref = applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode
        val editor = pref.edit()
        editor.putString("id", body.id.toString())
        editor.apply()

    }
}