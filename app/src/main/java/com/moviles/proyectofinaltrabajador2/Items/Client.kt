package com.moviles.proyectofinaltrabajador2.Items

import com.google.gson.annotations.SerializedName
import com.moviles.proyectofinaltrabajador2.models.Usuario

class Client(
    @SerializedName("id"      ) var id     : Int?    = null,
    @SerializedName("phone"   ) var phone  : String? = null,
    @SerializedName("user_id" ) var userId : Int?    = null,
    @SerializedName("user"    ) var user   : Usuario?   = Usuario()
) {
}