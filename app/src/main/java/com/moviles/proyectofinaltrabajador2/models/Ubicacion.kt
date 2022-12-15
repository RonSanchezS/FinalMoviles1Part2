package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName

class Ubicacion(
    @SerializedName("location" ) var location : Location? = Location()

) {
}