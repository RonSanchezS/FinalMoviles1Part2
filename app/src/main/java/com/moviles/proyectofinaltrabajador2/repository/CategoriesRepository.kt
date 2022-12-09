package com.moviles.proyectofinaltrabajador2.repository

import android.util.Log
import com.moviles.proyectofinaltrabajador2.apis.CategoriesApi
import com.moviles.proyectofinaltrabajador2.models.Categories
import com.moviles.proyectofinaltrabajador2.models.CategoriesPostResponse
import com.moviles.proyectofinaltrabajador2.models.Category
import com.moviles.proyectofinaltrabajador2.models.CategoryPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CategoriesRepository {

    fun getCategories(listener: onCategoriesListener, token : String) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(CategoriesApi::class.java)
        service.getCategories(token).enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()
                    listener.onSuccess(categories)
                    println("Categorias: $categories")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("Category", "Error")
                listener.onError(t)
            }
        })
    }

    fun postCategoryForWorker(categoria : CategoryPost, listener : onCategoriesPostListener, token : String){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(CategoriesApi::class.java)
        service.postCategory(categoria, token).enqueue(object : Callback<CategoriesPostResponse>{
            override fun onResponse(
                call: Call<CategoriesPostResponse>,
                response: Response<CategoriesPostResponse>
            ) {
                listener.onSuccess(response.body())
            }

            override fun onFailure(call: Call<CategoriesPostResponse>, t: Throwable) {
                listener.onError(t)
            }

        })

        }

    interface onCategoriesPostListener {
        fun onSuccess(body: CategoriesPostResponse?)
        fun onError(t: Throwable)

    }
    interface onCategoriesListener {
        fun onSuccess(categories: List<Category>?)
        fun onError(t: Throwable)

    }
}



