package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName


data class Worker (

    @SerializedName("id") val id : Int,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("email"    ) var email    : String? = null,
    @SerializedName("password" ) var password : String? = null,
    @SerializedName("phone"    ) var phone    : String? = null

){
    override fun toString(): String {
        return "worker(name=$name, email=$email, password=$password, phone=$phone)"
    }
}