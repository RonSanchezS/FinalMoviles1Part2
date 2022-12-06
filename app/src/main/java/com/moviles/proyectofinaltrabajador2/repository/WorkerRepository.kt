package com.moviles.proyectofinaltrabajador2.repository

import com.moviles.proyectofinaltrabajador2.apis.WorkerApi
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

object WorkerRepository {
    fun getWorkerCompleto(id : Int,listener : onCapacidadListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
        service.getWorkerCompleto(id.toString()).enqueue(object : Callback<WorkerCompleto> {
            override fun onFailure(call: Call<WorkerCompleto>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<WorkerCompleto>, response: Response<WorkerCompleto>) {
                if(response.isSuccessful){
                    val workerCompleto = response.body()
                    listener.onSuccess(workerCompleto)
                }else{
                    listener.onFailure(Throwable("Error en la respuesta"))
                }
            }

        })

    }

    interface onCapacidadListener {
        fun onFailure(t: Throwable)
        fun onSuccess(workerCompleto: WorkerCompleto?)

    }
}