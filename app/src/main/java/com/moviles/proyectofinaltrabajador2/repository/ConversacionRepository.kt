package com.moviles.proyectofinaltrabajador2.repository

import com.moviles.proyectofinaltrabajador2.apis.MensajesApi
import com.moviles.proyectofinaltrabajador2.models.Charla
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

    interface onGetCharlaListener {
        fun onGetCharlaFailure(t: Throwable)
        fun onGetCharlaSuccess(body: Any)

    }

}