package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository
import com.moviles.proyectofinaltrabajador2.repository.ImageController
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository
import java.lang.Exception

class ActivitySubirFotoTrabajador : AppCompatActivity(),
    ConversacionRepository.onProfilePictureUploadListener, WorkerRepository.onAlgoListener {
    private lateinit var btnSubirFoto: Button
    private lateinit var imgPreview: ImageView
    private lateinit var btnUpload: Button
    private lateinit var selectedFile: Uri

    private lateinit var idTrabajador: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subir_foto_trabajador)
        setUpListView()
        setUpListeners()
        idTrabajador = intent.getStringExtra("idTrabajador").toString()
    }

    private fun setUpListeners() {

        btnSubirFoto.setOnClickListener {
            ImageController.selectPhotoFromGallery(resultLauncher)
        }
        btnUpload.setOnClickListener {
            sendImage(ImageController.getImage(this))
            return@setOnClickListener
        }

    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                storeImage(result.data?.data!!)
            }
        }

    private fun storeImage(dataUri: Uri) {
        ImageController.saveImage(this, 1, dataUri)
        imgPreview.setImageURI(dataUri)
    }

    private fun sendImage(image: Uri) {

        val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
        if (token != null) {
            WorkerRepository.postProfilePicture(idTrabajador, token, image, this)
        }

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

    private fun setUpListView() {
        btnSubirFoto = findViewById(R.id.button2)
        imgPreview = findViewById(R.id.imageView)
        btnUpload = findViewById(R.id.button3)
    }

    override fun onProfilePictureFailed(exception: Exception) {
        finish()
    }

    override fun onProfilePictureSuccess(body: Mensaje) {
        finish()
    }

    override fun onProfilePictureFailed(t: Throwable) {
        finish()
    }

    override fun onProfilePictureSuccess() {
        finish()
    }
}