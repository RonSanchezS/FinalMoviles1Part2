package com.moviles.proyectofinaltrabajador2.repository

import com.moviles.proyectofinaltrabajador2.apis.MensajesApi
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object ConversacionRepository {

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

    interface onEnviarMensajeListener {
        fun onEnviarMensajeFailure(t: Throwable)
        fun onEnviarMensajeSuccess(body: Charla)

    }

    interface onGetCharlaListener {
        fun onGetCharlaFailure(t: Throwable)
        fun onGetCharlaSuccess(body: Any)

    }

}