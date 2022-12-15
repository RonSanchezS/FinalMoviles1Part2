package com.moviles.proyectofinaltrabajador2.repository

import android.net.Uri
import com.moviles.proyectofinaltrabajador2.apis.ConversacionApi
import com.moviles.proyectofinaltrabajador2.apis.CotizacionesApi
import com.moviles.proyectofinaltrabajador2.apis.MensajesApi
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Imagen
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception


object ConversacionRepository {

    fun uploadProfilePicture(idCotizacion : String, token : String, fileUri: Uri, listener : onProfilePictureUploadListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(ConversacionApi::class.java)
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
        service.uploadProfilePicture(idCotizacion.toInt(), "Bearer $token", filePart).enqueue(
            object : Callback<Mensaje> {
                override fun onFailure(call: Call<Mensaje>, t: Throwable) {
                    listener.onProfilePictureFailed(t as Exception)
                }

                override fun onResponse(call: Call<Mensaje>, response: Response<Mensaje>) {
                    if (response.isSuccessful) {
                        listener.onProfilePictureSuccess(response.body()!!)
                    } else {
                        listener.onProfilePictureFailed(java.lang.Exception("Error al subir la imagen"))
                    }
                }
            }
        )



    }

    interface onProfilePictureUploadListener {
        fun onProfilePictureFailed(exception: Exception)
        fun onProfilePictureSuccess(body: Mensaje)

    }

    fun getCharlas(id : String, token : String, listener : onGetCharlaListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val mensajesApi = retrofit.create(MensajesApi::class.java)
        mensajesApi.getMensajes(id, token).enqueue(object : Callback<List<Charla>> {
            override fun onFailure(call: Call<List<Charla>>, t: Throwable) {
                listener.onGetCharlaFailure(t)
            }

            override fun onResponse(call: Call<List<Charla>>, response: Response<List<Charla>>) {
                if(response.isSuccessful){
                    listener.onGetCharlaSuccess(response.body()!!)
                }else{
                    listener.onGetCharlaFailure(Throwable("Error al obtener las charlas"))
                }
            }
        })
    }
    fun enviarMensaje(token : String, id : String, mensaje : Mensaje, listener : onEnviarMensajeListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val mensajesApi = retrofit.create(MensajesApi::class.java)
        mensajesApi.enviarMensaje(id.toInt(), "Bearer $token", mensaje).enqueue(object : Callback<Charla> {
            override fun onFailure(call: Call<Charla>, t: Throwable) {
                listener.onEnviarMensajeFailure(t)
            }

            override fun onResponse(call: Call<Charla>, response: Response<Charla>) {
                if(response.isSuccessful){
                    listener.onEnviarMensajeSuccess(response.body()!!)
                }else{
                    println(response)
                }
            }
        })
    }


    fun sendImage(token : String, id : String, imagen : Imagen, listener : onImagenSentListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val mensajesApi = retrofit.create(MensajesApi::class.java)
        mensajesApi.enviarImagen(id.toInt(), "Bearer $token", imagen).enqueue(object : Callback<Charla> {
            override fun onFailure(call: Call<Charla>, t: Throwable) {
                listener.onImagenSentFailure(t)
                println(t.message)
            }

            override fun onResponse(call: Call<Charla>, response: Response<Charla>) {
                if(response.isSuccessful){
                    listener.onImagenSentSuccess(response.body()!!)
                }else{
                    println(response.errorBody())

                    listener.onImagenSentFailure(Throwable("Error al enviar la imagen"))
                }
            }
        })

    }

    fun descartarCotizacion(id : String, token : String, listener : onCotizacionDescartadaListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val cotizacionesApi = retrofit.create(CotizacionesApi::class.java)
        cotizacionesApi.discardWork(id.toInt(), "Bearer $token").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                listener.onDescartarCotizacionFailure(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    listener.onDescartarCotizacionSuccess()
                }else{
                    listener.onDescartarCotizacionFailure(Throwable("Error al descartar la cotizacion"))
                }
            }
        })
    }

    interface onCotizacionDescartadaListener {
        fun onDescartarCotizacionFailure(t: Throwable)
        fun onDescartarCotizacionSuccess()

    }

    fun finalizarCotizacion(id : String,token : String, listener : onCotizacionFinalizadaListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val cotizacionesApi = retrofit.create(CotizacionesApi::class.java)
        cotizacionesApi.finishWork(id.toInt(), "Bearer $token").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                listener.onCotizacionFinalizadaFailure(t)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    listener.onCotizacionFinalizadaSuccess()
                }else{
                    listener.onCotizacionFinalizadaFailure(Throwable("Error al finalizar la cotizacion"))
                }
            }
        })
    }

    interface onCotizacionFinalizadaListener {
        fun onCotizacionFinalizadaFailure(t: Throwable)
        fun onCotizacionFinalizadaSuccess()

    }

    interface onImagenSentListener {
        fun onImagenSentFailure(t: Throwable)
        fun onImagenSentSuccess(body: Charla)

    }

    interface onEnviarMensajeListener {
        fun onEnviarMensajeFailure(t: Throwable)
        fun onEnviarMensajeSuccess(body: Charla)

    }

    interface onGetCharlaListener {
        fun onGetCharlaFailure(t: Throwable)
        fun onGetCharlaSuccess(body: Any)

    }

}