package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.models.Imagen
import com.moviles.proyectofinaltrabajador2.models.Precio
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface WorkerApi {


    @Headers("Content-Type: application/json")
    @GET("worker/{id}")
    fun getWorkerCompleto(@Path("id") id: String, @Header("Authorization") token : String): Call<WorkerCompleto>

   // @Headers("Authorization: Bearer 89|MI8jytv2cqhMLExO9tL3VliwDXgsgrJYp7SLTl5l")
    @GET("worker/works")
    fun getCotizaciones(@Header("Authorization")token : String): Call<List<CotizacionConpleta>>

    @POST("worker/{id}/picture")
    fun post(@Path("id") id : Int, @Header("Authorization") token : String,@Part filePart : MultipartBody.Part): Call<Precio>

    @POST("work/{id}/price")
    fun ofertarCotizacion(@Path("id") id : String, @Header("Authorization") token : String, @Body precio : Precio) : Call<CotizacionConpleta>
}