package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.models.*
import retrofit2.Call
import retrofit2.http.*

interface LoginApi {

    //get token in sharedprefferences and store it in a variable


    @POST("worker/register")
    fun register(@Body worker: Worker): Call<Response>

    @POST("worker/login")
    fun login(@Body worker: WorkerLogin): Call<LoginResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("me")
    fun getDatosDelLogin(@Header("Authorization") auth : String): Call<Usuario>
}