package com.moviles.proyectofinaltrabajador2.apis

import com.moviles.proyectofinaltrabajador2.models.CategoriesPostResponse
import com.moviles.proyectofinaltrabajador2.models.Category
import com.moviles.proyectofinaltrabajador2.models.CategoryPost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface CategoriesApi {

    @Headers("Content-Type: application/json")
    @GET("categories")
    fun getCategories(@Header("Authorization") token : String): Call<List<Category>>

    @Headers("Content-Type: application/json")
    @POST("worker/category")
    fun postCategory(@Body categoria : CategoryPost, @Header("Authorization") token : String): Call<CategoriesPostResponse>
}