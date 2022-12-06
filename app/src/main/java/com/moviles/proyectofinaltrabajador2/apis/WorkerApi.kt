package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.models.Worker
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface WorkerApi {


    @Headers("Content-Type: application/json", "Authorization: Bearer 89|MI8jytv2cqhMLExO9tL3VliwDXgsgrJYp7SLTl5l")
    @GET("worker/{id}")
    fun getWorkerCompleto(@Path("id") id: String): Call<WorkerCompleto>

    @Headers("Authorization: Bearer 89|MI8jytv2cqhMLExO9tL3VliwDXgsgrJYp7SLTl5l")
    @GET("worker/works")
    fun getCotizaciones(): Call<List<CotizacionConpleta>>
}