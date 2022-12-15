package com.moviles.proyectofinaltrabajador2.repository

import android.net.Uri
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.apis.WorkerApi
import com.moviles.proyectofinaltrabajador2.models.Precio
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object WorkerRepository {


    fun getWorkerCompleto(id: String, listener: onCapacidadListener, token: String) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
        service.getWorkerCompleto(id.toString(), token).enqueue(object : Callback<WorkerCompleto> {
            override fun onFailure(call: Call<WorkerCompleto>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(
                call: Call<WorkerCompleto>,
                response: Response<WorkerCompleto>
            ) {
                if (response.isSuccessful) {
                    val workerCompleto = response.body()
                    listener.onSuccess(workerCompleto)
                } else {
                    listener.onFailure(Throwable("Error en la respuesta"))
                }
            }

        })

    }

    fun getCotizaciones(listenerCotizacion: onCotizacionListener, token: String) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
        service.getCotizaciones(token).enqueue(object : Callback<List<CotizacionConpleta>> {
            override fun onFailure(call: Call<List<CotizacionConpleta>>, t: Throwable) {
                listenerCotizacion.onFailure(t)
            }

            override fun onResponse(
                call: Call<List<CotizacionConpleta>>,
                response: Response<List<CotizacionConpleta>>
            ) {
                if (response.isSuccessful) {
                    val cotizaciones = response.body()
                    listenerCotizacion.onSuccess(cotizaciones)

                } else {
                    listenerCotizacion.onFailure(Throwable("Error en la respuesta"))
                }
            }

        })

    }

    fun ofertarCotizacion(token: String, id: String, listener: listenerCotizacion, precio: Precio) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
        service.ofertarCotizacion(id, token, precio).enqueue(object : Callback<CotizacionConpleta> {
            override fun onFailure(call: Call<CotizacionConpleta>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(
                call: Call<CotizacionConpleta>,
                response: Response<CotizacionConpleta>
            ) {
                if (response.isSuccessful) {
                    val cotizaciones = response.body()
                    listener.onSuccess(cotizaciones)
                } else {
                    listener.onFailure(Throwable("Error en la respuesta"))
                }
            }

        })

    }

    fun postProfilePicture(id: String, token: String, fileUri: Uri, listener: onAlgoListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
        val file = fileUri.path?.let { File(it) }
        if (file == null) {
            listener.onProfilePictureFailed(java.lang.Exception("No se pudo obtener el archivo"))
            return
        }
        val filePart = MultipartBody.Part.createFormData(
            "image",
            file.name,
            RequestBody.create(MediaType.parse("image/*"), file)
        )
        service.post(id.toInt(), token, filePart).enqueue(object : Callback<Precio> {
            override fun onResponse(call: Call<Precio>, response: Response<Precio>) {
                listener.onProfilePictureSuccess()
            }

            override fun onFailure(call: Call<Precio>, t: Throwable) {
                listener.onProfilePictureFailed(t)
            }

        })

    }

    interface onAlgoListener {
        fun onProfilePictureFailed(t: Throwable)
        fun onProfilePictureSuccess()

    }

    interface listenerCotizacion {
        fun onFailure(t: Throwable)
        fun onSuccess(cotizaciones: CotizacionConpleta?)
    }

    interface onCotizacionListener {
        fun onFailure(t: Throwable)
        fun onSuccess(cotizaciones: List<CotizacionConpleta>?)

    }

    interface onCapacidadListener {
        fun onFailure(t: Throwable)
        fun onSuccess(workerCompleto: WorkerCompleto?)

    }
}