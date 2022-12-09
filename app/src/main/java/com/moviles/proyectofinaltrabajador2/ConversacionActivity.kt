package com.moviles.proyectofinaltrabajador2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Adapters.CharlaAdapter
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository

class ConversacionActivity : AppCompatActivity(), ConversacionRepository.onGetCharlaListener {

    private lateinit var recyclerMensaje: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversacion)
        setUpListView()
        setUpApiCall()
    }

    private fun setUpListView() {
        recyclerMensaje = findViewById(R.id.recyclerMensaje)
    }

    private fun setUpApiCall() {
        val id = intent.extras?.get("idCotizacion").toString()
        //get token from shared prefferences
        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")

        println("ID : $id")
        println("TOKEN : $token")


        if (token != null) {
            ConversacionRepository.getCharlas(id, "Bearer $token", this)
        }
    }

    override fun onGetCharlaFailure(t: Throwable) {
       Toast.makeText(this, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onGetCharlaSuccess(body: Any) {
        Toast.makeText(this, "Success: ${body}", Toast.LENGTH_SHORT).show()
        val adapter = CharlaAdapter(body as ArrayList<Charla>)
        recyclerMensaje.layoutManager = LinearLayoutManager(this)
        recyclerMensaje.adapter = adapter

    }
}