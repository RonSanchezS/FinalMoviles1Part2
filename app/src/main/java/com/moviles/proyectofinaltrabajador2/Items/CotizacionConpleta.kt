package com.moviles.proyectofinaltrabajador2.Items

import com.google.android.gms.common.api.Api
import com.google.gson.annotations.SerializedName
import com.moviles.proyectofinaltrabajador2.models.Category
import com.moviles.proyectofinaltrabajador2.models.Worker

class CotizacionConpleta(

    @SerializedName("id"                ) var id                : Int?      = null,
    @SerializedName("client_id"         ) var clientId          : Int?      = null,
    @SerializedName("worker_id"         ) var workerId          : Int?      = null,
    @SerializedName("category_id"       ) var categoryId        : Int?      = null,
    @SerializedName("status"            ) var status            : Int?      = null,
    @SerializedName("priceOffer"        ) var priceOffer        : String?   = null,
    @SerializedName("review"            ) var review            : String?   = null,
    @SerializedName("deliveryLatitude"  ) var deliveryLatitude  : String?   = null,
    @SerializedName("deliveryLongitude" ) var deliveryLongitude : String?   = null,
    @SerializedName("deliveryAddress"   ) var deliveryAddress   : String?   = null,
    @SerializedName("client"            ) var client            : Client?   = Client(),
    @SerializedName("worker"            ) var worker            : Worker?   = Worker(),
    @SerializedName("category"          ) var category          : Category? = Category()


) {
    override fun toString(): String {
        return "CotizacionConpleta(id=$id, clientId=$clientId, workerId=$workerId, categoryId=$categoryId, status=$status, priceOffer=$priceOffer, review=$review, deliveryLatitude=$deliveryLatitude, deliveryLongitude=$deliveryLongitude, deliveryAddress=$deliveryAddress, client=$client, worker=$worker, category=$category)"
    }
}