package com.moviles.proyectofinaltrabajador2.models

import com.google.gson.annotations.SerializedName

data class WorkerCompleto(
    @SerializedName("id"             ) var id             : Int?                  = null,
    @SerializedName("phone"          ) var phone          : String?               = null,
    @SerializedName("profilePicture" ) var profilePicture : String?               = null,
    @SerializedName("reviewAvg"      ) var reviewAvg      : String?               = null,
    @SerializedName("user"           ) var user           : Usuario?                 = Usuario(),
    @SerializedName("categories"     ) var categories     : ArrayList<Categories> = arrayListOf()
) {
}