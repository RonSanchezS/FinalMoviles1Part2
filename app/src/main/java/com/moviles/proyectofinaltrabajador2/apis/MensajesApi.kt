package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Imagen
import com.moviles.proyectofinaltrabajador2.models.Mensaje
import retrofit2.Call
import retrofit2.http.*

interface MensajesApi {

    @GET("work/{id}/messages")
    fun getMensajes(@Path("id") id: String, @Header("Authorization") token: String): Call<List<Charla>>

    @POST("work/{id}/chat")
    fun enviarMensaje(@Path("id") id: Int, @Header("Authorization") token: String, @Body mensaje : Mensaje): Call<Charla>

    @Multipart
    @POST("work/{id}/chat")
    fun enviarImagen(@Path("id") id: Int, @Header("Authorization") token: String,  @Part image : Imagen): Call<Charla>
}