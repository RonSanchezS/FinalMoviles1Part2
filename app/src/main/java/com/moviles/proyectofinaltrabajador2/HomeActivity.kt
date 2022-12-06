package com.moviles.proyectofinaltrabajador2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moviles.proyectofinaltrabajador2.repository.LoginRepository

class HomeActivity : AppCompatActivity(), LoginRepository.onDatosLoginListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        LoginRepository.getDatosLogin(this)
    }

    override fun onFailure(t: Throwable) {
        println("Error: ${t.message}")
    }
}