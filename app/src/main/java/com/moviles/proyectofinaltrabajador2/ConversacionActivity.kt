package com.moviles.proyectofinaltrabajador2

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas.ActivitySeleccionarUbicacion
import com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas.Ubicacion
import com.moviles.proyectofinaltrabajador2.Adapters.CharlaAdapter
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Location
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository
import com.moviles.proyectofinaltrabajador2.repository.ImageController
import java.lang.Exception


class ConversacionActivity : AppCompatActivity(), ConversacionRepository.onGetCharlaListener,
    ConversacionRepository.onEnviarMensajeListener, ConversacionRepository.onImagenSentListener,
    ConversacionRepository.onProfilePictureUploadListener, CharlaAdapter.onMapClickListener {

    private lateinit var recyclerMensaje: RecyclerView

    private lateinit var btnEnviar: ImageButton
    private lateinit var inputTexto: EditText

    private lateinit var btnEnviarUbicacion: ImageButton
    private lateinit var btnEnviarImagen: ImageButton

    private lateinit var selectedFile: Uri

    private var id: String = ""

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
            val intent = Intent(this, ActivitySeleccionarUbicacion::class.java)
            intent.putExtra("idCotizacion", id)
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
        id = intent.extras?.get("idCotizacion").toString()
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
        val adapter = CharlaAdapter(body as ArrayList<Charla>, idLocal!!, this)
        recyclerMensaje.layoutManager = LinearLayoutManager(this)
        recyclerMensaje.adapter = adapter

    }

    override fun onEnviarMensajeFailure(t: Throwable) {
        setUpApiCall()

    }

    override fun onEnviarMensajeSuccess(body: Charla) {
        setUpApiCall()
    }

    override fun onImagenSentFailure(t: Throwable) {
        setUpApiCall()
    }

    override fun onImagenSentSuccess(body: Charla) {
        setUpApiCall()
    }

    override fun onProfilePictureFailed(exception: Exception) {
        setUpApiCall()
    }

    override fun onProfilePictureSuccess(body: Mensaje) {
        setUpApiCall()
    }

    override fun onMapClick(charla: Charla) {
        Toast.makeText(this, charla.toString(), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Ubicacion::class.java)
        intent.putExtra("latitud", charla.latitude.toString())
        intent.putExtra("longitud", charla.longitude.toString())
        startActivity(intent)
    }

}