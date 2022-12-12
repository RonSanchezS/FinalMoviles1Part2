package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.models.Mensaje
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ConversacionApi {

    @Multipart
    @POST("work/{id}/chat")
    fun uploadProfilePicture(@Path("id") id : Int, @Header("Authorization") token : String, @Part filePart : MultipartBody.Part): Call<Mensaje>

}