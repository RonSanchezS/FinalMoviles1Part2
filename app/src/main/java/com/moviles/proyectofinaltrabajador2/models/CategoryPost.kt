package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName

class CategoryPost (
    @SerializedName("category_name" ) var categoryName : String? = null,
    @SerializedName("description"   ) var description  : String? = null
        ){

}