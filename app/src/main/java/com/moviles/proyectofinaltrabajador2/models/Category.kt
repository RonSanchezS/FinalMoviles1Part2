package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName

class Category(

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("icon" ) var icon : String? = null
) {
    override fun toString(): String {
        return "$name"
    }
}