package com.moviles.proyectofinaltrabajador2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Adapters.CharlaAdapter
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Imagen
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ConversacionActivity : AppCompatActivity(), ConversacionRepository.onGetCharlaListener,
    ConversacionRepository.onEnviarMensajeListener, ConversacionRepository.onImagenSentListener {

    private lateinit var recyclerMensaje: RecyclerView

    private lateinit var btnEnviar: ImageButton
    private lateinit var inputTexto: EditText

    private lateinit var btnEnviarUbicacion: ImageButton
    private lateinit var btnEnviarImagen: ImageButton

    private lateinit var selectedFile: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversacion)
        setUpListView()
        setUpApiCall()
        setUpListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            selectedFile = data?.data!! // The URI with the location of the file
            uploadFile(selectedFile)
        }
    }

    private fun uploadFile(fileUri: Uri) {

        println(fileUri)

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
                    inputTexto.setText("")
                }

            }
        }
        btnEnviarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 111)

        }
        btnEnviarUbicacion.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)

            startActivity(intent)
        }
    }

    private fun setUpListView() {
        recyclerMensaje = findViewById(R.id.recyclerMensaje)
        btnEnviar = findViewById(R.id.btnEnviarMensaje)
        inputTexto = findViewById(R.id.inputTexto)
        btnEnviarUbicacion = findViewById(R.id.btnEnviarUbicacion)
        btnEnviarImagen = findViewById(R.id.btnEnviarImagen)
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

    override fun onImagenSentFailure(t: Throwable) {
        println(t.toString())
    }

    override fun onImagenSentSuccess(body: Charla) {
        TODO("Not yet implemented")
    }

}