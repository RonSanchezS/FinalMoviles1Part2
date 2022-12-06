package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName

class Categories(

    @SerializedName("description" ) var description : String?   = null,
    @SerializedName("category"    ) var category    : Category? = Category()
) {
}