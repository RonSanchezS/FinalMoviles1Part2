package com.moviles.proyectofinaltrabajador2.repository

import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.apis.WorkerApi
import com.moviles.proyectofinaltrabajador2.models.Precio
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WorkerRepository {
    fun getWorkerCompleto(id : Int,listener : onCapacidadListener, token  : String){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
        service.getWorkerCompleto(id.toString(), token).enqueue(object : Callback<WorkerCompleto> {
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

    fun getCotizaciones(listenerCotizacion : onCotizacionListener, token : String){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
       service.getCotizaciones(token).enqueue(object : Callback<List<CotizacionConpleta>> {
            override fun onFailure(call: Call<List<CotizacionConpleta>>, t: Throwable) {
                listenerCotizacion.onFailure(t)
            }

            override fun onResponse(call: Call<List<CotizacionConpleta>>, response: Response<List<CotizacionConpleta>>) {
                if(response.isSuccessful){
                    val cotizaciones = response.body()
                    listenerCotizacion.onSuccess(cotizaciones)

                }else{
                    listenerCotizacion.onFailure(Throwable("Error en la respuesta"))
                }
            }

        })

    }
    fun ofertarCotizacion(token : String , id : String,listener : listenerCotizacion, precio : Precio){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(WorkerApi::class.java)
        service.ofertarCotizacion(id, token, precio).enqueue(object : Callback<CotizacionConpleta> {
            override fun onFailure(call: Call<CotizacionConpleta>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<CotizacionConpleta>, response: Response<CotizacionConpleta>) {
                if(response.isSuccessful){
                    val cotizaciones = response.body()
                    listener.onSuccess(cotizaciones)
                }else{
                    listener.onFailure(Throwable("Error en la respuesta"))
                }
            }

        })

    }

    interface listenerCotizacion {
        fun onFailure(t : Throwable)
        fun onSuccess(cotizaciones: CotizacionConpleta?)
    }

    interface onCotizacionListener {
        fun onFailure(t: Throwable)
        fun onSuccess(cotizaciones: List<CotizacionConpleta>?)

    }

    interface onCapacidadListener {
        fun onFailure(t: Throwable)
        fun onSuccess(workerCompleto: WorkerCompleto?)

    }
}