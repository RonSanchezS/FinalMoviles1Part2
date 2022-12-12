package com.moviles.proyectofinaltrabajador2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.Adapters.CharlaAdapter
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Imagen
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository.sendImage
import com.moviles.proyectofinaltrabajador2.repository.ImageController
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception


class ConversacionActivity : AppCompatActivity(), ConversacionRepository.onGetCharlaListener,
    ConversacionRepository.onEnviarMensajeListener, ConversacionRepository.onImagenSentListener,
    ConversacionRepository.onProfilePictureUploadListener {

    private lateinit var recyclerMensaje: RecyclerView

    private lateinit var btnEnviar: ImageButton
    private lateinit var inputTexto: EditText

    private lateinit var btnEnviarUbicacion: ImageButton
    private lateinit var btnEnviarImagen: ImageButton

    private lateinit var selectedFile: Uri

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                storeImage(result.data?.data!!)
            }
        }

    private fun storeImage(dataUri: Uri) {
        ImageController.saveImage(this, 1, dataUri)
    }

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


    private fun sendImage(image: Uri) {
        val id = intent.extras?.get("idCotizacion").toString()
        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
        if (token != null) {
            ConversacionRepository.uploadProfilePicture(id, token, image, this)
        }

    }

    private fun setUpListeners() {
        btnEnviar.setOnClickListener {

            if (inputTexto.text.isEmpty()) {
                sendImage(ImageController.getImage(this))
                return@setOnClickListener
            }

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
            ImageController.selectPhotoFromGallery(resultLauncher)


        }
        btnEnviarUbicacion.setOnClickListener {
            val latitud = intent.extras?.get("latitud")
            val longitud = intent.extras?.get("longitud")
            val instruccion = intent.extras?.get("instruccion")

            //if there isnt latitud or longitud, then we disable the button
            if (latitud == null || longitud == null) {
                Toast.makeText(this, "No hay ubicacion para acceder", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("latitud", latitud.toString())
            intent.putExtra("longitud", longitud.toString())
            intent.putExtra("instruccion", instruccion.toString())
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
        setUpApiCall()    }

    override fun onEnviarMensajeSuccess(body: Charla) {
        setUpApiCall()
    }

    override fun onImagenSentFailure(t: Throwable) {
        setUpApiCall()    }

    override fun onImagenSentSuccess(body: Charla) {
        setUpApiCall()
    }

    override fun onProfilePictureFailed(exception: Exception) {
        setUpApiCall()
    }

    override fun onProfilePictureSuccess(body: Mensaje) {
        setUpApiCall()
    }

}