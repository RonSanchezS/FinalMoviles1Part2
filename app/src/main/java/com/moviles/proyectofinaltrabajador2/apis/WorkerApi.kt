package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.models.Precio
import com.moviles.proyectofinaltrabajador2.models.Worker
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkerApi {


    @Headers("Content-Type: application/json")
    @GET("worker/{id}")
    fun getWorkerCompleto(@Path("id") id: String, @Header("Authorization") token : String): Call<WorkerCompleto>

   // @Headers("Authorization: Bearer 89|MI8jytv2cqhMLExO9tL3VliwDXgsgrJYp7SLTl5l")
    @GET("worker/works")
    fun getCotizaciones(@Header("Authorization")token : String): Call<List<CotizacionConpleta>>

    @POST("work/{id}/price")
    fun ofertarCotizacion(@Path("id") id : String, @Header("Authorization") token : String, @Body precio : Precio) : Call<CotizacionConpleta>
}