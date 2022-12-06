package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moviles.proyectofinaltrabajador2.Items.CotizacionConpleta
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.models.WorkerCompleto
import com.moviles.proyectofinaltrabajador2.repository.WorkerRepository

class ActivityVerCapacidades : AppCompatActivity(), WorkerRepository.onCapacidadListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_capacidades)

        WorkerRepository.getWorkerCompleto(1, this)
    }

    override fun onFailure(t: Throwable) {
        //
    }

    override fun onSuccess(workerCompleto: WorkerCompleto?) {
    println("workerCompleto: ${workerCompleto}")


    }


}