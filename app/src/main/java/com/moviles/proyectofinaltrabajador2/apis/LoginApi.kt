package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.models.LoginResponse
import com.moviles.proyectofinaltrabajador2.models.Response
import com.moviles.proyectofinaltrabajador2.models.Worker
import com.moviles.proyectofinaltrabajador2.models.WorkerLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("worker/register")
    fun register(@Body worker: Worker): Call<Response>

    @POST("worker/login")
    fun login(@Body worker: WorkerLogin): Call<LoginResponse>
}