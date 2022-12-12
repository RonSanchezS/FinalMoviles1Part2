package com.moviles.proyectofinaltrabajador2.apis

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CotizacionesApi {

    @POST("work/{id}/finish")
    fun finishWork(@Path("id") id: Int, @Header("Authorization") token : String): Call<ResponseBody>

    @POST("work/{id}/dismiss")
    fun discardWork(@Path("id") id: Int, @Header("Authorization") token : String): Call<ResponseBody>
}