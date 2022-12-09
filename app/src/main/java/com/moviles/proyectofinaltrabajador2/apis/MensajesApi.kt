package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.models.Charla
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MensajesApi {

    @GET("work/{id}/messages")
    fun getMensajes(@Path("id") id: String, @Header("Authorization") token: String): Call<List<Charla>>
}