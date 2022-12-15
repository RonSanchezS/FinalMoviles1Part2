package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName

class Ubicacion(
    @SerializedName("latitude"  ) var latitude  : String? = null,
    @SerializedName("longitude" ) var longitude : String? = null
) {
}