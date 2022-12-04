package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName

data class WorkerLogin(

    @SerializedName("email"    ) var email    : String? = null,
    @SerializedName("password" ) var password : String? = null,
    @SerializedName("notification_id"    ) var notification_id    : String? = null
) {
    override fun toString(): String {
        return "WorkerLogin(email=$email, password=$password, notification_id=$notification_id)"
    }
}