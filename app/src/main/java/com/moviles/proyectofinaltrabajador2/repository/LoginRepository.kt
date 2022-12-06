package com.moviles.proyectofinaltrabajador2.repository

import android.content.Context
import android.widget.Toast
import com.moviles.proyectofinaltrabajador2.apis.LoginApi
import com.moviles.proyectofinaltrabajador2.models.LoginResponse
import com.moviles.proyectofinaltrabajador2.models.Usuario
import com.moviles.proyectofinaltrabajador2.models.Worker
import com.moviles.proyectofinaltrabajador2.models.WorkerLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRepository {

    fun register(worker: Worker, context: Context, listener: onRegisterListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(LoginApi::class.java)
        service.register(worker)
            .enqueue(object : Callback<com.moviles.proyectofinaltrabajador2.models.Response> {
                override fun onResponse(
                    call: Call<com.moviles.proyectofinaltrabajador2.models.Response>,
                    response: Response<com.moviles.proyectofinaltrabajador2.models.Response>
                ) {
                    listener.onSuccess(response.body()!!)
                }

                override fun onFailure(
                    call: Call<com.moviles.proyectofinaltrabajador2.models.Response>,
                    t: Throwable
                ) {
                    listener.onFailure(t)
                }

            })
    }

    interface onRegisterListener {
        fun onSuccess(body: com.moviles.proyectofinaltrabajador2.models.Response)
        fun onFailure(t: Throwable)

    }

    fun login(worker: WorkerLogin, context: Context, loginlistener: onLoginListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(LoginApi::class.java)
        service.login(worker)
            .enqueue(object : Callback<com.moviles.proyectofinaltrabajador2.models.LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    loginlistener.onSuccess(response.body()!!)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginlistener.onFailure(t)
                }


            })
    }

    fun getDatosLogin(listener: onDatosLoginListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(LoginApi::class.java)
        service.getDatosDelLogin()
            .enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    //listener.onSuccess(response.body()!!)
                    println(response.body())


                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    listener.onFailure(t)
                }


            })
    }

    interface onDatosLoginListener {
        fun onFailure(t: Throwable)

    }

    interface onLoginListener {
        fun onSuccess(body: LoginResponse)
        fun onFailure(t: Throwable)

    }
}


