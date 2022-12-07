package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.models.Categories
import com.moviles.proyectofinaltrabajador2.models.CategoriesPostResponse
import com.moviles.proyectofinaltrabajador2.models.Category
import com.moviles.proyectofinaltrabajador2.models.CategoryPost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface CategoriesApi {

    @Headers("Content-Type: application/json", "Authorization: Bearer 148|OlGH4olDg5xVa0RUX75oHC0C2wStUP8tWU3bI7BQ")
    @GET("categories")
    fun getCategories(): Call<List<Category>>

    @Headers("Content-Type: application/json", "Authorization: Bearer 148|OlGH4olDg5xVa0RUX75oHC0C2wStUP8tWU3bI7BQ")
    @POST("worker/category")
    fun postCategory(@Body categoria : CategoryPost): Call<CategoriesPostResponse>
}