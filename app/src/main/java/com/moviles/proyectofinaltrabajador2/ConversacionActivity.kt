package com.moviles.proyectofinaltrabajador2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Adapters.CharlaAdapter
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository

class ConversacionActivity : AppCompatActivity(), ConversacionRepository.onGetCharlaListener,
    ConversacionRepository.onEnviarMensajeListener {

    private lateinit var recyclerMensaje: RecyclerView

    private lateinit var btnEnviar: ImageButton
    private lateinit var inputTexto: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversacion)
        setUpListView()
        setUpApiCall()
        setUpListeners()
    }

    private fun setUpListeners() {
        btnEnviar.setOnClickListener {
            val texto = inputTexto.text.toString()
            if (texto.isNotEmpty()) {
                val mensaje = Mensaje(texto)
                val id = intent.extras?.get("idCotizacion").toString()
                val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")

                println("Mensaje: $mensaje id: $id token: $token")
                if (token != null) {
                    ConversacionRepository.enviarMensaje(token, id, mensaje, this)
                }

            }
        }
    }

    private fun setUpListView() {
        recyclerMensaje = findViewById(R.id.recyclerMensaje)
        btnEnviar = findViewById(R.id.btnEnviarMensaje)
        inputTexto = findViewById(R.id.inputTexto)
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
        //  Toast.makeText(this, "Success: ${body}", Toast.LENGTH_SHORT).show()
        val idLocal = getSharedPreferences("MyPref", MODE_PRIVATE).getString("id", "")
        val adapter = CharlaAdapter(body as ArrayList<Charla>, idLocal!!)
        recyclerMensaje.layoutManager = LinearLayoutManager(this)
        recyclerMensaje.adapter = adapter

    }

    override fun onEnviarMensajeFailure(t: Throwable) {
        println(t.message)
    }

    override fun onEnviarMensajeSuccess(body: Charla) {
        inputTexto.setText("nada")
      //  setUpApiCall()
    }
}